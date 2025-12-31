package com.franciscor.edutrackmultiplatform.data

import com.franciscor.edutrackmultiplatform.model.Anio
import com.franciscor.edutrackmultiplatform.model.Asignatura
import com.franciscor.edutrackmultiplatform.model.Notas
import com.franciscor.edutrackmultiplatform.model.Usuario
import com.franciscor.edutrackmultiplatform.util.calcularPromedioNotas
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.DatabaseReference
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.first
import kotlin.random.Random

sealed class LoginResult {
    data class Success(val user: Usuario) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

sealed class RegisterResult {
    data class Success(val user: Usuario) : RegisterResult()
    data class Error(val message: String) : RegisterResult()
}

class UserRepository {
    private val auth: FirebaseAuth
    private val rootRef: DatabaseReference
    private val userRef: DatabaseReference
    private val anioRef: DatabaseReference
    private val asignaturaRef: DatabaseReference

    init {
        Firebase.initialize()
        auth = Firebase.auth
        val database = Firebase.database
        rootRef = database.reference("Edutrack")
        userRef = rootRef.child("Usuario")
        anioRef = rootRef.child("Anio")
        asignaturaRef = rootRef.child("Asignatura")
    }

    suspend fun restoreUser(): Usuario? {
        val current = auth.currentUser ?: return null
        val baseUser = fetchUser(current.uid) ?: createUserRecord(current, password = null)
        val anios = fetchAnios(current.uid)
        return baseUser.copy(anio = anios.toMutableList())
    }

    suspend fun login(identifier: String, password: String): LoginResult {
        val normalized = identifier.trim()
        val email = if (normalized.contains("@")) {
            normalized
        } else {
            resolveEmailForUsername(normalized)
        }
        if (email.isNullOrBlank()) {
            return LoginResult.Error("User not found")
        }
        return try {
            val result = auth.signInWithEmailAndPassword(email, password)
            val firebaseUser = result.user ?: return LoginResult.Error("Unable to sign in")
            val baseUser = fetchUser(firebaseUser.uid) ?: createUserRecord(firebaseUser, password)
            val anios = fetchAnios(firebaseUser.uid)
            LoginResult.Success(baseUser.copy(anio = anios.toMutableList()))
        } catch (e: Exception) {
            LoginResult.Error("Invalid credentials")
        }
    }

    suspend fun register(nombre: String, email: String, password: String): RegisterResult {
        val trimmedName = nombre.trim()
        val trimmedEmail = email.trim()
        if (trimmedName.isBlank() || trimmedEmail.isBlank() || password.isBlank()) {
            return RegisterResult.Error("Complete all fields")
        }
        return try {
            val result = auth.createUserWithEmailAndPassword(trimmedEmail, password)
            val firebaseUser = result.user ?: return RegisterResult.Error("Unable to create account")
            val user = Usuario(
                id = firebaseUser.uid,
                nombre = trimmedName.ifBlank { resolveDefaultName(firebaseUser, trimmedEmail) },
                email = trimmedEmail,
                password = password,
            )
            userRef.child(firebaseUser.uid).setValue(user) { encodeDefaults = true }
            firebaseUser.sendEmailVerification()
            RegisterResult.Success(user)
        } catch (e: Exception) {
            RegisterResult.Error("Unable to register user")
        }
    }

    suspend fun updateUser(user: Usuario) {
        val id = user.id ?: return
        val updates = mutableMapOf<String, Any?>()
        if (!user.nombre.isNullOrBlank()) updates["nombre"] = user.nombre
        if (!user.email.isNullOrBlank()) updates["email"] = user.email
        if (!user.password.isNullOrBlank()) updates["password"] = user.password
        if (updates.isNotEmpty()) {
            userRef.child(id).updateChildren(updates)
        }
    }

    suspend fun deleteUser(userId: String) {
        try {
            val aniosSnapshot = anioRef.orderByChild("id_user").equalTo(userId).valueEvents.first()
            aniosSnapshot.children.forEach { anioSnapshot ->
                val asignaturasSnapshot = anioSnapshot.child("lista_asignaturas")
                asignaturasSnapshot.children.forEach { child ->
                    val asignaturaId = child.key ?: return@forEach
                    asignaturaRef.child(asignaturaId).removeValue()
                }
                anioSnapshot.ref.removeValue()
            }
            userRef.child(userId).removeValue()
            auth.currentUser?.delete()
            auth.signOut()
        } catch (e: Exception) {
            auth.signOut()
        }
    }

    suspend fun signOut() {
        auth.signOut()
    }

    suspend fun createAnio(
        nombre: String,
        descripcion: String,
        fechaInicio: String,
        fechaFin: String,
        numeroAsignaturas: Int,
        userId: String,
    ): Anio? {
        return try {
            val ref = anioRef.push()
            val id = ref.key ?: randomId("anio")
            val anio = Anio(
                id = id,
                nombre = nombre,
                descripcion = descripcion,
                fechaInicio = fechaInicio,
                fechaFin = fechaFin,
                numero_asignaturas = numeroAsignaturas,
                lista_asignaturas = emptyMap(),
                id_user = userId,
            )
            ref.setValue(anio) { encodeDefaults = true }
            anio
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteAnio(anioId: String) {
        try {
            val asignaturasSnapshot = anioRef.child(anioId).child("lista_asignaturas").valueEvents.first()
            asignaturasSnapshot.children.forEach { child ->
                val asignaturaId = child.key ?: return@forEach
                asignaturaRef.child(asignaturaId).removeValue()
            }
            anioRef.child(anioId).removeValue()
        } catch (e: Exception) {
        }
    }

    suspend fun createAsignatura(
        anioId: String,
        userId: String?,
        input: Asignatura,
    ): Asignatura? {
        return try {
            val key = keyFromName(input.nombre) ?: asignaturaRef.push().key ?: randomId("asig")
            val asignatura = input.copy(
                id = key,
                id_anio = anioId,
                id_usuario = userId,
            )
            val dbAsignatura = stripNotas(asignatura)
            asignaturaRef.child(key).setValue(dbAsignatura) { encodeDefaults = true }
            anioRef.child(anioId).child("lista_asignaturas").child(key).setValue(dbAsignatura) { encodeDefaults = true }
            asignatura
        } catch (e: Exception) {
            null
        }
    }

    suspend fun deleteAsignatura(anioId: String, asignaturaId: String) {
        try {
            asignaturaRef.child(asignaturaId).removeValue()
            anioRef.child(anioId).child("lista_asignaturas").child(asignaturaId).removeValue()
        } catch (e: Exception) {
        }
    }

    suspend fun saveNota(anioId: String, asignaturaId: String, nota: Notas, media: Double) {
        try {
            val notaId = nota.id?.takeIf { it.isNotBlank() }
                ?: asignaturaRef.child(asignaturaId).child("Notas").push().key
                ?: randomId("nota")
            val notaFinal = nota.copy(id = notaId, id_asignatura = asignaturaId)
            asignaturaRef.child(asignaturaId).child("Notas").child(notaId).setValue(notaFinal) { encodeDefaults = true }
            updateAsignaturaMedia(anioId, asignaturaId, media)
        } catch (e: Exception) {
        }
    }

    suspend fun deleteNota(anioId: String, asignaturaId: String, notaId: String, media: Double) {
        try {
            asignaturaRef.child(asignaturaId).child("Notas").child(notaId).removeValue()
            updateAsignaturaMedia(anioId, asignaturaId, media)
        } catch (e: Exception) {
        }
    }

    private suspend fun updateAsignaturaMedia(anioId: String, asignaturaId: String, media: Double) {
        val updates = mapOf("media" to media)
        asignaturaRef.child(asignaturaId).updateChildren(updates)
        anioRef.child(anioId).child("lista_asignaturas").child(asignaturaId).updateChildren(updates)
    }

    private suspend fun fetchUser(userId: String): Usuario? {
        return try {
            val snapshot = userRef.child(userId).valueEvents.first()
            if (!snapshot.exists) return null
            val user = snapshot.value<Usuario>()
            user.copy(id = userId)
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun fetchAnios(userId: String): List<Anio> {
        val snapshot = try {
            anioRef.orderByChild("id_user").equalTo(userId).valueEvents.first()
        } catch (e: Exception) {
            return emptyList()
        }

        val anios = snapshot.children.mapNotNull { child ->
            val anio = runCatching { child.value<Anio>() }.getOrNull() ?: return@mapNotNull null
            val id = anio.id ?: child.key ?: return@mapNotNull anio
            anio.copy(id = id, lista_asignaturas = anio.lista_asignaturas ?: emptyMap())
        }
        return hydrateNotas(anios)
    }

    private suspend fun hydrateNotas(anios: List<Anio>): List<Anio> =
        anios.map { anio ->
            val asignaturas = anio.lista_asignaturas ?: emptyMap()
            if (asignaturas.isEmpty()) return@map anio
            val updated = asignaturas.mapValues { (id, asignatura) ->
                val notas = loadNotas(id)
                val media = if (notas.isNotEmpty()) calcularPromedioNotas(notas) else (asignatura.media ?: 0.0)
                asignatura.copy(id = id, lista_notas = notas.toMutableList(), media = media)
            }
            anio.copy(lista_asignaturas = updated)
        }

    private suspend fun loadNotas(asignaturaId: String): List<Notas> {
        val snapshot = try {
            asignaturaRef.child(asignaturaId).child("Notas").valueEvents.first()
        } catch (e: Exception) {
            return emptyList()
        }
        if (!snapshot.exists) return emptyList()
        return snapshot.children.mapNotNull { child ->
            val nota = runCatching { child.value<Notas>() }.getOrNull() ?: return@mapNotNull null
            val id = nota.id ?: child.key
            nota.copy(id = id, id_asignatura = asignaturaId)
        }
    }

    private suspend fun resolveEmailForUsername(username: String): String? {
        return try {
            val snapshot = userRef.orderByChild("nombre").equalTo(username).valueEvents.first()
            val userSnapshot = snapshot.children.firstOrNull() ?: return null
            val usuario = runCatching { userSnapshot.value<Usuario>() }.getOrNull()
            usuario?.email ?: runCatching { userSnapshot.child("email").value<String>() }.getOrNull()
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun createUserRecord(user: FirebaseUser, password: String?): Usuario {
        val email = user.email.orEmpty()
        val usuario = Usuario(
            id = user.uid,
            nombre = resolveDefaultName(user, email),
            email = email,
            password = password,
        )
        userRef.child(user.uid).setValue(usuario) { encodeDefaults = true }
        return usuario
    }

    private fun resolveDefaultName(user: FirebaseUser, email: String): String {
        return user.displayName
            ?: email.substringBefore("@").ifBlank { "Usuario" }
    }

    private fun keyFromName(nombre: String?): String? {
        return nombre
            ?.lowercase()
            ?.replace("\\s+".toRegex(), "_")
            ?.replace("[^a-z0-9_\\-]".toRegex(), "")
            ?.takeIf { it.isNotBlank() }
    }

    private fun stripNotas(asignatura: Asignatura): Asignatura =
        asignatura.copy(lista_notas = null)

    private fun randomId(prefix: String): String {
        val suffix = Random.nextLong().toString(16)
        return "$prefix-$suffix"
    }
}
