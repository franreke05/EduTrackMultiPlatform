package com.franciscor.edutrackmultiplatform.data

import com.franciscor.edutrackmultiplatform.model.Usuario

sealed class LoginResult {
    data class Success(val user: Usuario) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

sealed class RegisterResult {
    data class Success(val user: Usuario) : RegisterResult()
    data class Error(val message: String) : RegisterResult()
}

class UserRepository {
    private val users = mutableListOf<Usuario>()
    private var nextId = 1

    fun login(email: String, password: String): LoginResult {
        val user = users.firstOrNull { it.email.equals(email, ignoreCase = true) && it.password == password }
        return if (user != null) {
            LoginResult.Success(user)
        } else {
            LoginResult.Error("Invalid credentials")
        }
    }

    fun register(nombre: String, email: String, password: String): RegisterResult {
        if (nombre.isBlank() || email.isBlank() || password.isBlank()) {
            return RegisterResult.Error("Complete all fields")
        }
        val exists = users.any { it.email.equals(email, ignoreCase = true) }
        if (exists) {
            return RegisterResult.Error("Email already registered")
        }
        val user = Usuario(
            id = nextId.toString(),
            nombre = nombre.trim(),
            email = email.trim(),
            password = password,
        )
        nextId += 1
        users.add(user)
        return RegisterResult.Success(user)
    }
}
