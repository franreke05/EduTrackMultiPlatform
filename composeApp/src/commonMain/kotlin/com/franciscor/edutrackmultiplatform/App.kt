package com.franciscor.edutrackmultiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.franciscor.edutrackmultiplatform.data.LoginResult
import com.franciscor.edutrackmultiplatform.data.RegisterResult
import com.franciscor.edutrackmultiplatform.data.UserRepository
import com.franciscor.edutrackmultiplatform.model.Anio
import com.franciscor.edutrackmultiplatform.model.Asignatura
import com.franciscor.edutrackmultiplatform.model.Notas
import com.franciscor.edutrackmultiplatform.model.Usuario
import com.franciscor.edutrackmultiplatform.platform.ensureFirebaseInitialized
import com.franciscor.edutrackmultiplatform.ui.AnioScreen
import com.franciscor.edutrackmultiplatform.ui.AuthScreen
import com.franciscor.edutrackmultiplatform.ui.CreacionAnioScreen
import com.franciscor.edutrackmultiplatform.ui.InicioScreen
import com.franciscor.edutrackmultiplatform.ui.NotasScreen
import com.franciscor.edutrackmultiplatform.ui.PerfilScreen
import com.franciscor.edutrackmultiplatform.ui.SplashScreen
import com.franciscor.edutrackmultiplatform.ui.theme.EduTrackTheme
import com.franciscor.edutrackmultiplatform.util.calcularPromedioNotas
import kotlin.random.Random
import kotlinx.coroutines.launch

sealed class Screen {
    data object Splash : Screen()
    data object Auth : Screen()
    data object Inicio : Screen()
    data object CrearAnio : Screen()
    data class Anio(val anioId: String) : Screen()
    data class Notas(val anioId: String, val asignaturaId: String) : Screen()
    data object Perfil : Screen()
}

@Composable
fun App(
    startScreen: Screen = Screen.Splash,
    startInRegister: Boolean = false,
) {
    ensureFirebaseInitialized()
    val repository = remember { UserRepository() }
    var currentUser by remember { mutableStateOf<Usuario?>(null) }
    val anios = remember { mutableStateListOf<Anio>() }
    val backStack = remember(startScreen) { mutableStateListOf<Screen>(startScreen) }
    val scope = rememberCoroutineScope()

    fun newId(prefix: String): String {
        val suffix = Random.nextLong().toString(16)
        return "$prefix-$suffix"
    }

    fun resetTo(screen: Screen) {
        backStack.clear()
        backStack.add(screen)
    }

    fun navigate(screen: Screen) {
        backStack.add(screen)
    }

    fun pop() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.size - 1)
        }
    }

    fun syncUserAnios() {
        currentUser = currentUser?.copy(anio = anios.toMutableList())
    }

    fun replaceAnio(updated: Anio) {
        val index = anios.indexOfFirst { it.id == updated.id }
        if (index >= 0) {
            anios[index] = updated
            syncUserAnios()
        }
    }

    fun deleteAnio(anioId: String) {
        val index = anios.indexOfFirst { it.id == anioId }
        if (index >= 0) {
            anios.removeAt(index)
            syncUserAnios()
        }
        scope.launch {
            repository.deleteAnio(anioId)
        }
    }

    fun addAnio(
        nombre: String,
        descripcion: String,
        fechaInicio: String,
        fechaFin: String,
        numeroAsignaturas: Int,
    ) {
        val userId = currentUser?.id ?: return
        scope.launch {
            val anio = repository.createAnio(
                nombre = nombre,
                descripcion = descripcion,
                fechaInicio = fechaInicio,
                fechaFin = fechaFin,
                numeroAsignaturas = numeroAsignaturas,
                userId = userId,
            ) ?: return@launch
            anios.add(anio)
            syncUserAnios()
        }
    }

    fun updateAsignatura(
        anioId: String,
        asignaturaId: String,
        transform: (Asignatura) -> Asignatura,
    ) {
        val index = anios.indexOfFirst { it.id == anioId }
        if (index < 0) return
        val anio = anios[index]
        val map = (anio.lista_asignaturas ?: emptyMap()).toMutableMap()
        val current = map[asignaturaId] ?: return
        map[asignaturaId] = transform(current)
        replaceAnio(anio.copy(lista_asignaturas = map))
    }

    fun addAsignatura(anioId: String, input: Asignatura) {
        val userId = currentUser?.id
        scope.launch {
            val asignatura = repository.createAsignatura(anioId, userId, input) ?: return@launch
            val index = anios.indexOfFirst { it.id == anioId }
            if (index < 0) return@launch
            val anio = anios[index]
            val map = (anio.lista_asignaturas ?: emptyMap()).toMutableMap()
            val id = asignatura.id ?: return@launch
            map[id] = asignatura
            replaceAnio(anio.copy(lista_asignaturas = map))
        }
    }

    fun deleteAsignatura(anioId: String, asignaturaId: String) {
        val index = anios.indexOfFirst { it.id == anioId }
        if (index < 0) return
        val anio = anios[index]
        val map = (anio.lista_asignaturas ?: emptyMap()).toMutableMap()
        if (map.remove(asignaturaId) != null) {
            replaceAnio(anio.copy(lista_asignaturas = map))
        }
        scope.launch {
            repository.deleteAsignatura(anioId, asignaturaId)
        }
    }

    fun saveNota(anioId: String, asignaturaId: String, nota: Notas) {
        updateAsignatura(anioId, asignaturaId) { asignatura ->
            val current = asignatura.lista_notas?.toMutableList() ?: mutableListOf()
            val id = nota.id?.takeIf { it.isNotBlank() } ?: newId("nota")
            val updatedNota = nota.copy(id = id, id_asignatura = asignaturaId)
            val index = current.indexOfFirst { it.id == id }
            if (index >= 0) {
                current[index] = updatedNota
            } else {
                current.add(updatedNota)
            }
            val media = calcularPromedioNotas(current)
            scope.launch {
                repository.saveNota(anioId, asignaturaId, updatedNota, media)
            }
            asignatura.copy(lista_notas = current, media = media)
        }
    }

    fun deleteNota(anioId: String, asignaturaId: String, notaId: String) {
        updateAsignatura(anioId, asignaturaId) { asignatura ->
            val current = asignatura.lista_notas?.toMutableList() ?: mutableListOf()
            val updated = current.filterNot { it.id == notaId }
            val media = calcularPromedioNotas(updated)
            scope.launch {
                repository.deleteNota(anioId, asignaturaId, notaId, media)
            }
            asignatura.copy(lista_notas = updated.toMutableList(), media = media)
        }
    }

    fun resolveAsignatura(anioId: String, asignaturaId: String): Asignatura? {
        val anio = anios.firstOrNull { it.id == anioId } ?: return null
        return anio.lista_asignaturas?.get(asignaturaId)
    }

    LaunchedEffect(Unit) {
        val restored = repository.restoreUser()
        if (restored != null) {
            currentUser = restored
            anios.clear()
            anios.addAll(restored.anio)
            val target = when (startScreen) {
                Screen.Splash, Screen.Auth -> Screen.Inicio
                else -> startScreen
            }
            resetTo(target)
        } else if (startScreen != Screen.Splash && startScreen != Screen.Auth) {
            resetTo(Screen.Auth)
        }
    }

    EduTrackTheme {
        when (val current = backStack.last()) {
            Screen.Splash -> SplashScreen(onFinished = {
                if (currentUser != null) {
                    resetTo(Screen.Inicio)
                } else {
                    resetTo(Screen.Auth)
                }
            })
            Screen.Auth -> AuthScreen(
                onLogin = { identifier, password ->
                    repository.login(identifier, password)
                },
                onRegister = { nombre, email, password ->
                    repository.register(nombre, email, password)
                },
                onAuthSuccess = { user ->
                    currentUser = user
                    anios.clear()
                    anios.addAll(user.anio)
                    resetTo(Screen.Inicio)
                },
                startInRegister = startInRegister,
            )
            Screen.Inicio -> InicioScreen(
                anios = anios,
                onAnioSelected = { anio ->
                    val id = anio.id ?: return@InicioScreen
                    navigate(Screen.Anio(id))
                },
                onCrearAnio = { navigate(Screen.CrearAnio) },
                onPerfil = { navigate(Screen.Perfil) },
                onDeleteAnio = { deleteAnio(it) },
            )
            Screen.CrearAnio -> CreacionAnioScreen(
                onBack = { pop() },
                onSave = { nombre, descripcion, fechaInicio, fechaFin, numeroAsignaturas ->
                    addAnio(nombre, descripcion, fechaInicio, fechaFin, numeroAsignaturas)
                    pop()
                },
            )
            is Screen.Anio -> {
                val anio = anios.firstOrNull { it.id == current.anioId }
                if (anio == null) {
                    LaunchedEffect(Unit) {
                        pop()
                    }
                } else {
                    AnioScreen(
                        anio = anio,
                        onBack = { pop() },
                        onOpenNotas = { asignatura ->
                            val asignaturaId = asignatura.id ?: return@AnioScreen
                            navigate(Screen.Notas(anio.id ?: "", asignaturaId))
                        },
                        onAddAsignatura = { anioId, asignatura ->
                            addAsignatura(anioId, asignatura)
                        },
                    )
                }
            }
            is Screen.Notas -> {
                val asignatura = resolveAsignatura(current.anioId, current.asignaturaId)
                if (asignatura == null) {
                    LaunchedEffect(Unit) {
                        pop()
                    }
                } else {
                    NotasScreen(
                        asignatura = asignatura,
                        onBack = { pop() },
                        onSaveNota = { nota ->
                            saveNota(current.anioId, current.asignaturaId, nota)
                        },
                        onDeleteNota = { nota ->
                            val id = nota.id ?: return@NotasScreen
                            deleteNota(current.anioId, current.asignaturaId, id)
                        },
                        onDeleteAsignatura = {
                            deleteAsignatura(current.anioId, current.asignaturaId)
                            pop()
                        },
                    )
                }
            }
            Screen.Perfil -> PerfilScreen(
                user = currentUser,
                anios = anios,
                onBack = { pop() },
                onSave = { nombre, password ->
                    val base = currentUser ?: return@PerfilScreen
                    val updated = base.copy(
                        nombre = nombre.ifBlank { base.nombre },
                        password = password.ifBlank { base.password },
                        anio = anios.toMutableList(),
                    )
                    currentUser = updated
                    scope.launch {
                        repository.updateUser(updated)
                    }
                },
                onDeleteAccount = {
                    val userId = currentUser?.id
                    scope.launch {
                        if (userId != null) {
                            repository.deleteUser(userId)
                        }
                    }
                    currentUser = null
                    anios.clear()
                    resetTo(Screen.Auth)
                },
                onLogout = {
                    scope.launch {
                        repository.signOut()
                    }
                    currentUser = null
                    anios.clear()
                    resetTo(Screen.Auth)
                },
            )
        }
    }
}
