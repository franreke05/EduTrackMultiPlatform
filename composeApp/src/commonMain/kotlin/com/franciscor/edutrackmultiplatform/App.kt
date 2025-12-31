package com.franciscor.edutrackmultiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.franciscor.edutrackmultiplatform.data.LoginResult
import com.franciscor.edutrackmultiplatform.data.RegisterResult
import com.franciscor.edutrackmultiplatform.data.UserRepository
import com.franciscor.edutrackmultiplatform.model.Usuario
import com.franciscor.edutrackmultiplatform.ui.HomeScreen
import com.franciscor.edutrackmultiplatform.ui.LoginScreen
import com.franciscor.edutrackmultiplatform.ui.SignUpScreen
import com.franciscor.edutrackmultiplatform.ui.SplashScreen
import com.franciscor.edutrackmultiplatform.ui.theme.EduTrackTheme

sealed class Screen {
    data object Splash : Screen()
    data object Login : Screen()
    data object SignUp : Screen()
    data class Home(val user: Usuario) : Screen()
}

@Composable
fun App() {
    val repository = remember { UserRepository() }
    var screen by remember { mutableStateOf<Screen>(Screen.Splash) }

    EduTrackTheme {
        when (val current = screen) {
            Screen.Splash -> SplashScreen(onTimeout = { screen = Screen.Login })
            Screen.Login -> LoginScreen(
                onLogin = { email, password ->
                    val result = repository.login(email, password)
                    if (result is LoginResult.Success) {
                        screen = Screen.Home(result.user)
                    }
                    result
                },
                onSignUp = { screen = Screen.SignUp },
            )
            Screen.SignUp -> SignUpScreen(
                onRegister = { nombre, email, password ->
                    val result = repository.register(nombre, email, password)
                    if (result is RegisterResult.Success) {
                        screen = Screen.Login
                    }
                    result
                },
                onCancel = { screen = Screen.Login },
            )
            is Screen.Home -> HomeScreen(
                user = current.user,
                onLogout = { screen = Screen.Login },
            )
        }
    }
}
