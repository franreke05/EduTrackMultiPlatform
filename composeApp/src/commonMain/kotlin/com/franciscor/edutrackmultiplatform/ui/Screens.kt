package com.franciscor.edutrackmultiplatform.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.franciscor.edutrackmultiplatform.data.LoginResult
import com.franciscor.edutrackmultiplatform.data.RegisterResult
import com.franciscor.edutrackmultiplatform.model.Usuario
import edutrackmultiplatform.composeapp.generated.resources.Res
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val scale = remember { Animatable(0.5f) }
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1.2f,
            animationSpec = tween(
                durationMillis = 2000,
                easing = FastOutSlowInEasing,
            ),
        )
        delay(500)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF00BCD4), Color(0xFF3F51B5)),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(Res.drawable.agendita),
            contentDescription = stringResource(Res.string.splash_logo),
            modifier = Modifier
                .size(200.dp)
                .scale(scale.value),
        )
    }
}

@Composable
fun LoginScreen(
    onLogin: suspend (String, String) -> LoginResult,
    onSignUp: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
    ) {
        val screenHeight = maxHeight

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.051f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF00BCD4), Color(0xFF3F51B5)),
                        ),
                    ),
                contentAlignment = Alignment.Center,
            ) {
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.2f))

            Text(
                text = stringResource(Res.string.login_title),
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF8E9090),
                modifier = Modifier.padding(bottom = screenHeight * 0.014f),
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(screenHeight * 0.02f))
                    .shadow(screenHeight * 0.41f),
                colors = CardDefaults.cardColors(containerColor = Color.White),
            ) {
                Column(
                    modifier = Modifier.padding(screenHeight * 0.01f),
                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.01f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(Res.string.login_username_label)) },
                        placeholder = { Text(stringResource(Res.string.login_username_placeholder)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(Res.string.login_password_label)) },
                        placeholder = { Text(stringResource(Res.string.login_password_placeholder)) },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(screenHeight * 0.01f),
                modifier = Modifier.fillMaxWidth(0.9f),
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            when (val result = onLogin(email, password)) {
                                is LoginResult.Success -> error = null
                                is LoginResult.Error -> error = result.message
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                ) {
                    Text(stringResource(Res.string.login_sign_in), color = Color.White)
                }

                OutlinedButton(
                    onClick = onSignUp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.06f),
                    shape = RoundedCornerShape(25.dp),
                ) {
                    Text(stringResource(Res.string.login_sign_up), color = Color(0xFF3F51B5))
                }
            }

            if (error != null) {
                Spacer(modifier = Modifier.height(screenHeight * 0.02f))
                Text(
                    text = error.orEmpty(),
                    color = Color(0xFFD32F2F),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.286f))

            Text(
                text = stringResource(Res.string.login_footer),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(0.9f),
            )
        }
    }
}

@Composable
fun SignUpScreen(
    onRegister: suspend (String, String, String) -> RegisterResult,
    onCancel: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
    ) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.051f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF00BCD4), Color(0xFF3F51B5)),
                        ),
                    ),
            ) {
                Text(
                    text = stringResource(Res.string.signup_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.0505f))

            Image(
                painter = painterResource(Res.drawable.agendita),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.3f),
            )

            Spacer(modifier = Modifier.height(screenHeight * 0.015f))

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(screenHeight * 0.02f))
                    .shadow(screenHeight * 0.41f),
                colors = CardDefaults.cardColors(containerColor = Color.White),
            ) {
                Column(
                    modifier = Modifier.padding(screenHeight * 0.01f),
                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.01f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text(stringResource(Res.string.signup_email_label)) },
                        placeholder = { Text(stringResource(Res.string.signup_email_placeholder)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text(stringResource(Res.string.signup_username_label)) },
                        placeholder = { Text(stringResource(Res.string.signup_username_placeholder)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text(stringResource(Res.string.signup_password_label)) },
                        placeholder = { Text(stringResource(Res.string.signup_password_placeholder)) },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.05f))

            Row(
                horizontalArrangement = Arrangement.spacedBy(screenHeight * 0.01f),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            when (val result = onRegister(username, email, password)) {
                                is RegisterResult.Success -> error = null
                                is RegisterResult.Error -> error = result.message
                            }
                        }
                    },
                    modifier = Modifier
                        .width(screenWidth * 0.5f)
                        .height(screenHeight * 0.06f)
                        .padding(start = screenHeight * 0.06f),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CD964)),
                ) {
                    Image(
                        painter = painterResource(Res.drawable.baseline_check_24),
                        contentDescription = stringResource(Res.string.signup_create),
                        modifier = Modifier.size(screenHeight * 0.04f),
                    )
                }

                Button(
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.06f)
                        .padding(end = screenHeight * 0.06f),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B30)),
                ) {
                    Image(
                        painter = painterResource(Res.drawable.baseline_cancel_24),
                        contentDescription = stringResource(Res.string.signup_cancel),
                        modifier = Modifier.size(screenHeight * 0.04f),
                    )
                }
            }

            if (error != null) {
                Spacer(modifier = Modifier.height(screenHeight * 0.02f))
                Text(
                    text = error.orEmpty(),
                    color = Color(0xFFD32F2F),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(screenHeight * 0.286f))

            Text(
                text = stringResource(Res.string.login_footer),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(0.9f),
            )
        }
    }
}

@Composable
fun HomeScreen(
    user: Usuario,
    onLogout: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(Res.string.home_welcome, user.nombre),
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = stringResource(Res.string.home_subtitle),
            style = MaterialTheme.typography.bodyLarge,
        )
        Button(onClick = onLogout) {
            Text(stringResource(Res.string.home_logout))
        }
    }
}
