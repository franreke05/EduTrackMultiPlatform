@file:OptIn(
    androidx.compose.material3.ExperimentalMaterial3Api::class,
    org.jetbrains.compose.resources.ExperimentalResourceApi::class,
)

package com.franciscor.edutrackmultiplatform.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.franciscor.edutrackmultiplatform.data.LoginResult
import com.franciscor.edutrackmultiplatform.data.RegisterResult
import com.franciscor.edutrackmultiplatform.model.Anio
import com.franciscor.edutrackmultiplatform.model.Asignatura
import com.franciscor.edutrackmultiplatform.model.Notas
import com.franciscor.edutrackmultiplatform.model.Usuario
import com.franciscor.edutrackmultiplatform.util.abbreviateName
import com.franciscor.edutrackmultiplatform.util.abreviarNombre
import com.franciscor.edutrackmultiplatform.util.calcularMediaAnio
import com.franciscor.edutrackmultiplatform.util.calcularMediaConjunta
import com.franciscor.edutrackmultiplatform.util.calcularPromedioNotas
import com.franciscor.edutrackmultiplatform.util.formatDecimal1
import com.franciscor.edutrackmultiplatform.util.formatMedia
import com.franciscor.edutrackmultiplatform.util.formatPercent
import com.franciscor.edutrackmultiplatform.util.toRoman
import edutrackmultiplatform.composeapp.generated.resources.Res
import edutrackmultiplatform.composeapp.generated.resources.agendita
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

private enum class AuthMode { Login, Register }

@Composable
fun SplashScreen(onFinished: () -> Unit) {
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
        onFinished()
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
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .scale(scale.value),
        )
    }
}

@Composable
fun AuthScreen(
    onLogin: suspend (String, String) -> LoginResult,
    onRegister: suspend (String, String, String) -> RegisterResult,
    onAuthSuccess: (Usuario) -> Unit,
    startInRegister: Boolean = false,
) {
    var authMode by remember(startInRegister) {
        mutableStateOf(if (startInRegister) AuthMode.Register else AuthMode.Login)
    }
    var email by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF00BCD4), Color(0xFF3F51B5)),
                ),
            ),
    ) {
        val screenHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = screenHeight * 0.06f)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(24.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Image(
                        painter = painterResource(Res.drawable.agendita),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(screenHeight * 0.12f)
                            .clip(CircleShape),
                    )

                    Text(
                        text = if (authMode == AuthMode.Login) "Iniciar sesion" else "Crear cuenta",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Text(
                        text = if (authMode == AuthMode.Login) {
                            "Accede con tu cuenta para sincronizar tus datos."
                        } else {
                            "Registrate con tu correo para continuar."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Button(
                        onClick = {
                            error = "Google sign-in not available yet"
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        shape = MaterialTheme.shapes.extraLarge,
                        enabled = !isLoading,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Surface(
                                    modifier = Modifier.size(32.dp),
                                    color = Color.Transparent,
                                    shape = CircleShape,
                                    border = BorderStroke(
                                        1.dp,
                                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                                    ),
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "G",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                }
                                Text(
                                    text = "Continue with Google",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                            Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                        }
                    }

                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = if (authMode == AuthMode.Login) "Email or user" else "Email") },
                        placeholder = {
                            Text(
                                text = if (authMode == AuthMode.Login) {
                                    "correo@ejemplo.com o usuario"
                                } else {
                                    "correo@ejemplo.com"
                                },
                            )
                        },
                        singleLine = true,
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            cursorColor = MaterialTheme.colorScheme.primary,
                        ),
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Email,
                        ),
                    )

                    if (authMode == AuthMode.Register) {
                        OutlinedTextField(
                            value = displayName,
                            onValueChange = { displayName = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = "Nombre o usuario") },
                            placeholder = { Text(text = "Tu nombre visible") },
                            singleLine = true,
                            shape = MaterialTheme.shapes.extraLarge,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                cursorColor = MaterialTheme.colorScheme.primary,
                            ),
                            keyboardOptions = KeyboardOptions(autoCorrect = false),
                        )
                    }

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Contrasena") },
                        placeholder = { Text(text = "********") },
                        singleLine = true,
                        shape = MaterialTheme.shapes.extraLarge,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (passwordVisible) "Ocultar contrasena" else "Mostrar contrasena",
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            cursorColor = MaterialTheme.colorScheme.primary,
                        ),
                        keyboardOptions = KeyboardOptions(autoCorrect = false, keyboardType = KeyboardType.Password),
                    )

                    if (authMode == AuthMode.Register) {
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = "Confirmar contrasena") },
                            placeholder = { Text(text = "Repite tu contrasena") },
                            singleLine = true,
                            shape = MaterialTheme.shapes.extraLarge,
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                cursorColor = MaterialTheme.colorScheme.primary,
                            ),
                            keyboardOptions = KeyboardOptions(autoCorrect = false, keyboardType = KeyboardType.Password),
                        )
                    }

                    Button(
                        onClick = {
                            if (isLoading) return@Button
                            error = null
                            if (email.isBlank() || password.isBlank()) {
                                error = "Completa los campos requeridos"
                                return@Button
                            }
                            if (authMode == AuthMode.Register) {
                                if (displayName.isBlank()) {
                                    error = "Completa el nombre"
                                    return@Button
                                }
                                if (confirmPassword.isBlank() || confirmPassword != password) {
                                    error = "Las contrasenas no coinciden"
                                    return@Button
                                }
                                if (password.length < 6) {
                                    error = "La contrasena debe tener al menos 6 caracteres"
                                    return@Button
                                }
                            }
                            isLoading = true
                            scope.launch {
                                if (authMode == AuthMode.Login) {
                                    when (val result = onLogin(email, password)) {
                                        is LoginResult.Success -> {
                                            isLoading = false
                                            onAuthSuccess(result.user)
                                        }
                                        is LoginResult.Error -> {
                                            isLoading = false
                                            error = result.message
                                        }
                                    }
                                } else {
                                    when (val result = onRegister(displayName, email, password)) {
                                        is RegisterResult.Success -> {
                                            isLoading = false
                                            onAuthSuccess(result.user)
                                        }
                                        is RegisterResult.Error -> {
                                            isLoading = false
                                            error = result.message
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF245D34)),
                        enabled = !isLoading,
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp),
                            )
                        } else {
                            Text(
                                text = if (authMode == AuthMode.Login) "Iniciar sesion" else "Crear cuenta",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }

                    if (error != null) {
                        Text(
                            text = error.orEmpty(),
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = if (authMode == AuthMode.Login) "No tienes cuenta?" else "Ya tienes cuenta?",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = if (authMode == AuthMode.Login) "Registrate aqui" else "Inicia sesion",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.extraSmall)
                                .clickable {
                                    authMode = if (authMode == AuthMode.Login) AuthMode.Register else AuthMode.Login
                                    confirmPassword = ""
                                    displayName = ""
                                    error = null
                                },
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                shape = MaterialTheme.shapes.extraLarge,
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "EduTrack",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Tus datos se guardan en tu cuenta y puedes recuperarlos en cualquier dispositivo.",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    Text(
                        text = "Al continuar aceptas nuestros terminos de servicio y privacidad.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
fun InicioScreen(
    anios: List<Anio>,
    onAnioSelected: (Anio) -> Unit,
    onCrearAnio: () -> Unit,
    onPerfil: () -> Unit,
    onDeleteAnio: (String) -> Unit,
) {
    val selectedAnios = remember { mutableStateMapOf<String, Anio>() }
    val porcentajes = remember { mutableStateMapOf<String, String>() }
    var mediaConjunta by remember { mutableStateOf<Double?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var anioToDelete by remember { mutableStateOf<Anio?>(null) }
    var displayedAnios by remember { mutableStateOf(anios) }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        LaunchedEffect(anios) {
            displayedAnios = anios
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = screenWidth * 0.04f, vertical = screenHeight * 0.04f),
                verticalArrangement = Arrangement.spacedBy(screenHeight * 0.02f),
            ) {
                Text(
                    text = "Tus anios academicos",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = screenHeight * 0.01f),
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    tonalElevation = 2.dp,
                    shadowElevation = 2.dp,
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface,
                ) {
                    AnimationSearch(
                        initialAnios = anios,
                        onAniosFiltered = { filteredList ->
                            displayedAnios = filteredList
                        },
                        screenHeight = screenHeight,
                        screenWidth = screenWidth,
                        onCrearAnio = onCrearAnio,
                        onPerfil = onPerfil,
                    )
                }

                LazyColumn(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(screenHeight * 0.015f),
                    contentPadding = PaddingValues(bottom = screenHeight * 0.04f),
                ) {
                    itemsIndexed(displayedAnios, key = { _, anio -> anio.id ?: anio.hashCode().toString() }) { index, anio ->
                        val mediaAnio = calcularMediaAnio(anio)
                        val isSelected = anio.id?.let { selectedAnios.containsKey(it) } == true
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { value ->
                                when (value) {
                                    SwipeToDismissBoxValue.StartToEnd -> {
                                        anio.id?.let { id ->
                                            if (isSelected) {
                                                selectedAnios.remove(id)
                                                porcentajes.remove(id)
                                            } else {
                                                selectedAnios[id] = anio
                                                porcentajes.putIfAbsent(id, "")
                                            }
                                            mediaConjunta = null
                                        }
                                        false
                                    }
                                    SwipeToDismissBoxValue.EndToStart -> {
                                        anioToDelete = anio
                                        showDeleteDialog = true
                                        false
                                    }
                                    else -> false
                                }
                            },
                        )

                        SwipeToDismissBox(
                            state = dismissState,
                            modifier = Modifier.fillMaxWidth(),
                            enableDismissFromStartToEnd = true,
                            enableDismissFromEndToStart = true,
                            backgroundContent = {
                                val color = when (dismissState.targetValue) {
                                    SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.error.copy(alpha = 0.9f)
                                    SwipeToDismissBoxValue.StartToEnd -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    else -> Color.Transparent
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(screenHeight * 0.08f)
                                        .background(color)
                                        .padding(horizontal = screenWidth * 0.05f),
                                    contentAlignment = when (dismissState.targetValue) {
                                        SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                                        else -> Alignment.CenterEnd
                                    },
                                ) {
                                    when (dismissState.targetValue) {
                                        SwipeToDismissBoxValue.StartToEnd -> {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Seleccionar",
                                                tint = MaterialTheme.colorScheme.primary,
                                                modifier = Modifier.size(screenHeight * 0.04f),
                                            )
                                        }
                                        SwipeToDismissBoxValue.EndToStart -> {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Eliminar",
                                                tint = MaterialTheme.colorScheme.onError,
                                                modifier = Modifier.size(screenHeight * 0.04f),
                                            )
                                        }
                                        else -> { }
                                    }
                                }
                            },
                        ) {
                            AnioCard(
                                anio = anio,
                                index = index + 1,
                                screenHeight = screenHeight,
                                screenWidth = screenWidth,
                                mediaAnio = mediaAnio,
                                isSelected = isSelected,
                            ) {
                                onAnioSelected(anio)
                            }
                        }
                    }
                }

                if (selectedAnios.isNotEmpty()) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.large,
                        tonalElevation = 3.dp,
                        shadowElevation = 3.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = screenWidth * 0.04f, vertical = screenHeight * 0.02f),
                            verticalArrangement = Arrangement.spacedBy(screenHeight * 0.015f),
                        ) {
                            Text(
                                text = "Anios seleccionados",
                                style = MaterialTheme.typography.titleMedium,
                            )

                            selectedAnios.values.forEach { anioSeleccionado ->
                                val id = anioSeleccionado.id ?: return@forEach
                                val textoPorcentaje = porcentajes[id] ?: ""
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.02f),
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(anioSeleccionado.nombre ?: "Sin nombre", style = MaterialTheme.typography.bodyLarge)
                                        calcularMediaAnio(anioSeleccionado)?.let { media ->
                                            Text(
                                                text = "Media: ${formatMedia(media)}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            )
                                        }
                                    }
                                    OutlinedTextField(
                                        value = textoPorcentaje,
                                        onValueChange = { nuevo ->
                                            if (nuevo.length <= 3 && nuevo.all { it.isDigit() }) {
                                                porcentajes[id] = nuevo
                                                mediaConjunta = null
                                            }
                                        },
                                        modifier = Modifier.width(screenWidth * 0.45f),
                                        singleLine = true,
                                        label = { Text("Porcentaje") },
                                        placeholder = { Text("0-100") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        suffix = { Text("%") },
                                    )
                                    TextButton(
                                        onClick = {
                                            selectedAnios.remove(id)
                                            porcentajes.remove(id)
                                            mediaConjunta = null
                                        },
                                    ) {
                                        Text("Quitar")
                                    }
                                }
                            }

                            Button(
                                onClick = {
                                    mediaConjunta = calcularMediaConjunta(selectedAnios, porcentajes)
                                },
                                modifier = Modifier.align(Alignment.End),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            ) {
                                Text("Mostrar media en conjunto")
                            }

                            mediaConjunta?.let { resultado ->
                                Text(
                                    text = "Media ponderada: ${formatMedia(resultado)}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Confirmar eliminacion") },
                text = { Text("Seguro que quieres eliminar el anio '${anioToDelete?.nombre}'?") },
                confirmButton = {
                    Button(
                        onClick = {
                            anioToDelete?.id?.let { anioId ->
                                if (anioId.isNotEmpty()) {
                                    onDeleteAnio(anioId)
                                }
                            }
                            showDeleteDialog = false
                            anioToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    ) {
                        Text("Eliminar", color = MaterialTheme.colorScheme.onError)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancelar")
                    }
                },
            )
        }
    }
}

@Composable
private fun AnioCard(
    anio: Anio,
    index: Int,
    screenHeight: Dp,
    screenWidth: Dp,
    mediaAnio: Double?,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (isSelected) 1.5.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = MaterialTheme.shapes.large,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.large,
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = screenWidth * 0.04f, vertical = screenHeight * 0.02f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.04f),
        ) {
            Column(
                modifier = Modifier.width(screenWidth * 0.18f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Surface(
                    modifier = Modifier.size(screenHeight * 0.06f),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    tonalElevation = 0.dp,
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = toRoman(index),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                    }
                }
                Text(
                    text = mediaAnio?.let { "Media: ${formatMedia(it)}" } ?: "Media: --",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }

            Column(
                modifier = Modifier.weight(0.6f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                anio.nombre?.let { Text(text = it, style = MaterialTheme.typography.titleMedium) }
                anio.descripcion?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                anio.numero_asignaturas?.let {
                    Text(
                        text = "Numero de asignaturas: $it",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Column(
                modifier = Modifier.weight(0.4f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = "Fecha inicio",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                anio.fechaInicio?.let { Text(text = it, style = MaterialTheme.typography.bodyMedium) }
                Text(
                    text = "Fecha fin",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                anio.fechaFin?.let { Text(text = it, style = MaterialTheme.typography.bodyMedium) }
            }
        }
    }
}

@Composable
private fun CircularActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    screenHeight: Dp,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(screenHeight * 0.02f),
    ) {
        Surface(
            modifier = Modifier.size(screenHeight * 0.07f),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 3.dp,
            shadowElevation = 3.dp,
        ) {
            IconButton(
                onClick = onClick,
                modifier = Modifier.fillMaxSize(),
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(screenHeight * 0.045f),
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(screenHeight * 0.09f),
        )
    }
}

@Composable
private fun AnimationSearch(
    initialAnios: List<Anio>,
    onAniosFiltered: (List<Anio>) -> Unit,
    screenHeight: Dp,
    screenWidth: Dp,
    onCrearAnio: () -> Unit,
    onPerfil: () -> Unit,
) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var showFilterMenu by remember { mutableStateOf(false) }
    var selectedSortOption by remember { mutableStateOf("Fecha Reciente") }

    val horizontalPadding = screenWidth * 0.04f

    val sortOptions = listOf(
        "Fecha Reciente",
        "Ultimo a primero",
        "Por nombre",
        "Por cantidad de asignaturas",
    )
    val actions = listOf(
        "Perfil" to Icons.Default.Person,
        "Buscar" to Icons.Default.Search,
        "Anadir" to Icons.Default.Add,
    )

    LaunchedEffect(searchQuery, selectedSortOption, initialAnios) {
        val filteredList =
            if (searchQuery.isNotBlank()) {
                initialAnios.filter { anio -> anio.nombre?.contains(searchQuery, ignoreCase = true) == true }
            } else {
                initialAnios
            }

        val sortedList = when (selectedSortOption) {
            "Fecha Reciente" -> filteredList.sortedByDescending { dateKey(it.fechaInicio) }
            "Ultimo a primero" -> filteredList.reversed()
            "Por nombre" -> filteredList.sortedBy { it.nombre }
            "Por cantidad de asignaturas" -> filteredList.sortedByDescending { it.lista_asignaturas?.size ?: 0 }
            else -> filteredList
        }

        onAniosFiltered(sortedList)
    }

    AnimatedContent(
        targetState = isSearchExpanded,
        modifier = Modifier.padding(horizontal = horizontalPadding, vertical = screenHeight * 0.02f),
    ) { isExpanded ->
        if (!isExpanded) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = screenHeight * 0.045f),
                horizontalArrangement = Arrangement.spacedBy(horizontalPadding),
            ) {
                items(actions) { (label, icon) ->
                    CircularActionButton(
                        icon = icon,
                        label = label,
                        onClick = {
                            when (label) {
                                "Buscar" -> isSearchExpanded = true
                                "Anadir" -> onCrearAnio()
                                "Perfil" -> onPerfil()
                                else -> { }
                            }
                        },
                        screenHeight = screenHeight,
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = screenHeight * 0.01f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(screenWidth * 0.02f),
            ) {
                IconButton(onClick = { isSearchExpanded = false; searchQuery = "" }) {
                    Icon(Icons.Default.SearchOff, contentDescription = "Cerrar busqueda")
                }
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar anio...") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                )
                Box {
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filtrar anios")
                    }
                    DropdownMenu(
                        expanded = showFilterMenu,
                        onDismissRequest = { showFilterMenu = false },
                    ) {
                        sortOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedSortOption = option
                                    showFilterMenu = false
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun dateKey(value: String?): Int {
    val parts = value?.split("/") ?: return Int.MIN_VALUE
    if (parts.size != 3) return Int.MIN_VALUE
    val day = parts[0].toIntOrNull() ?: return Int.MIN_VALUE
    val month = parts[1].toIntOrNull() ?: return Int.MIN_VALUE
    val year = parts[2].toIntOrNull() ?: return Int.MIN_VALUE
    return year * 10000 + month * 100 + day
}

@Composable
fun CreacionAnioScreen(
    onBack: () -> Unit,
    onSave: (String, String, String, String, Int) -> Unit,
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fechaInicio by remember { mutableStateOf("") }
    var fechaFin by remember { mutableStateOf("") }
    var numeroAsignaturas by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val spacing = 16.dp

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nuevo anio escolar") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Atras",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val numero = numeroAsignaturas.toIntOrNull()
                            if (nombre.isBlank() || fechaInicio.isBlank() || fechaFin.isBlank() || numero == null) {
                                error = "Completa los campos requeridos"
                                return@IconButton
                            }
                            if (numero !in 1..20) {
                                error = "Numero de asignaturas invalido"
                                return@IconButton
                            }
                            error = null
                            onSave(nombre, descripcion, fechaInicio, fechaFin, numero)
                        },
                    ) {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = "Guardar",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = spacing, vertical = spacing)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing),
            ) {
                Text(
                    text = "Informacion del anio",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth(),
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                ) {
                    Column(modifier = Modifier.padding(spacing)) {
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre del curso (ej. 2024-2025)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            label = { Text("Descripcion (opcional)") },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 4,
                        )
                    }
                }

                Text(
                    text = "Duracion",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth(),
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                ) {
                    Column(modifier = Modifier.padding(spacing)) {
                        OutlinedTextField(
                            value = fechaInicio,
                            onValueChange = { fechaInicio = it },
                            label = { Text("Fecha de inicio (dd/MM/yyyy)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = fechaFin,
                            onValueChange = { fechaFin = it },
                            label = { Text("Fecha de fin (dd/MM/yyyy)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                        )
                    }
                }

                Text(
                    text = "Numero de asignaturas",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth(),
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                ) {
                    Column(modifier = Modifier.padding(spacing)) {
                        OutlinedTextField(
                            value = numeroAsignaturas,
                            onValueChange = { nuevoValor ->
                                val textoFiltrado = nuevoValor.filter { it.isDigit() }
                                if (textoFiltrado.length <= 2) {
                                    numeroAsignaturas = textoFiltrado
                                }
                            },
                            label = { Text("Numero de asignaturas (max. 20)") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                        )
                    }
                }

                if (error != null) {
                    Text(
                        text = error.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
fun AnioScreen(
    anio: Anio,
    onBack: () -> Unit,
    onOpenNotas: (Asignatura) -> Unit,
    onAddAsignatura: (String, Asignatura) -> Unit,
) {
    var showDescriptionDialog by remember { mutableStateOf(false) }
    var showAsignaturaDialog by remember { mutableStateOf(false) }
    val maxAsignaturas = anio.numero_asignaturas ?: 0
    val actuales = anio.lista_asignaturas?.size ?: 0
    val anioLleno = actuales >= maxAsignaturas
    var showSearch by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val asignaturasBase = anio.lista_asignaturas?.values?.toList() ?: emptyList()
    val asignaturasFiltradas = if (showSearch && searchQuery.isNotBlank()) {
        asignaturasBase.filter { it.nombre?.contains(searchQuery, ignoreCase = true) == true }
    } else asignaturasBase
    val spacing = 16.dp

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(anio.nombre ?: "Anio escolar") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (anioLleno) {
                            error = "Limite de asignaturas alcanzado"
                        } else {
                            error = null
                            showAsignaturaDialog = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Anadir asignatura",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                    IconButton(onClick = {
                        showSearch = !showSearch
                        if (!showSearch) searchQuery = ""
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar asignatura",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = spacing, vertical = spacing),
            ) {
                if (showSearch) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("Buscar asignatura") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = spacing),
                        singleLine = true,
                    )
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = spacing),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(8.dp),
                ) {
                    BoxWithConstraints(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(spacing),
                    ) {
                        val screenHeight = maxHeight
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(spacing),
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(screenHeight * 0.15f)
                                    .aspectRatio(1.2f)
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(screenHeight * 0.02f),
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = toRoman(1),
                                    style = MaterialTheme.typography.displaySmall,
                                    color = if (anioLleno) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                IconButton(onClick = { showDescriptionDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowDown,
                                        contentDescription = "Ver descripcion",
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                }
                                Text(
                                    text = anio.nombre ?: "",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                )
                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Default.FilterList,
                                        contentDescription = "Filtrar asignaturas",
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            }
                        }
                    }
                }

                if (error != null) {
                    Text(
                        text = error.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = spacing),
                    )
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    itemsIndexed(asignaturasFiltradas) { index, asignatura ->
                        AsignaturaCard(index + 1, asignatura) {
                            onOpenNotas(asignatura)
                        }
                    }
                }
            }
        }
    }

    if (showDescriptionDialog) {
        AlertDialog(
            onDismissRequest = { showDescriptionDialog = false },
            title = { Text("Descripcion de ${anio.nombre}") },
            text = { Text(anio.descripcion ?: "No hay descripcion.") },
            confirmButton = {
                TextButton(onClick = { showDescriptionDialog = false }) {
                    Text("Cerrar")
                }
            },
        )
    }

    if (showAsignaturaDialog) {
        CrearAsignaturaDialog(
            anioId = anio.id,
            idUsuario = anio.id_user,
            maxAsignaturas = maxAsignaturas,
            actuales = actuales,
            onDismiss = { showAsignaturaDialog = false },
            onCreate = { asignatura ->
                val id = anio.id ?: return@CrearAsignaturaDialog
                onAddAsignatura(id, asignatura)
                showAsignaturaDialog = false
            },
        )
    }
}

@Composable
private fun AsignaturaCard(index: Int, asignatura: Asignatura, onClick: () -> Unit) {
    Card(
        modifier = Modifier.aspectRatio(1f),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = "$index", style = MaterialTheme.typography.titleLarge)
            Text(text = abbreviateName(asignatura.nombre ?: ""), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun CrearAsignaturaDialog(
    anioId: String?,
    idUsuario: String?,
    maxAsignaturas: Int,
    actuales: Int,
    onDismiss: () -> Unit,
    onCreate: (Asignatura) -> Unit,
) {
    val nombre = remember { mutableStateOf("") }
    val descripcion = remember { mutableStateOf("") }
    val creditos = remember { mutableStateOf("") }
    val tipo = remember { mutableStateOf("Trimestre") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva asignatura") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = nombre.value, onValueChange = { nombre.value = it }, label = { Text("Nombre") })
                OutlinedTextField(value = descripcion.value, onValueChange = { descripcion.value = it }, label = { Text("Descripcion") })
                OutlinedTextField(
                    value = creditos.value,
                    onValueChange = { creditos.value = it.filter { c -> c.isDigit() } },
                    label = { Text("Creditos") },
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Tipo:")
                    listOf("Trimestre", "Cuatrimestre").forEach { opcion ->
                        val selected = tipo.value == opcion
                        Button(
                            onClick = { tipo.value = opcion },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                            ),
                        ) { Text(opcion.take(3)) }
                    }
                }
                Text(
                    text = "Asignaturas actuales: $actuales / $maxAsignaturas",
                    style = MaterialTheme.typography.bodySmall,
                )
                if (error != null) {
                    Text(text = error.orEmpty(), color = MaterialTheme.colorScheme.error)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (anioId.isNullOrEmpty() || nombre.value.isBlank()) {
                    error = "Completa el nombre de la asignatura"
                    return@TextButton
                }
                if (actuales >= maxAsignaturas) {
                    error = "Limite de asignaturas alcanzado"
                    return@TextButton
                }
                val creditosInt = creditos.value.toIntOrNull() ?: 0
                val numeroPeriodos = if (tipo.value == "Cuatrimestre") 4 else 3
                val asignatura = Asignatura(
                    nombre = nombre.value,
                    descripcion = descripcion.value,
                    creditos = creditosInt,
                    id_usuario = idUsuario,
                    id_anio = anioId,
                    tipo_periodo = tipo.value,
                    numero_periodos = numeroPeriodos,
                )
                onCreate(asignatura)
            }) { Text("Crear") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
    )
}

@Composable
fun NotasScreen(
    asignatura: Asignatura,
    onBack: () -> Unit,
    onSaveNota: (Notas) -> Unit,
    onDeleteNota: (Notas) -> Unit,
    onDeleteAsignatura: () -> Unit,
) {
    val notasState = asignatura.lista_notas ?: mutableListOf()
    var showDialog by remember { mutableStateOf(false) }
    var notaEnEdicion by remember { mutableStateOf<Notas?>(null) }
    var showDeleteConfirm by remember { mutableStateOf<Notas?>(null) }
    var showDeleteAsignatura by remember { mutableStateOf(false) }
    var infoMessage by remember { mutableStateOf<String?>(null) }

    val promedio = calcularPromedioNotas(notasState)
    val porcentajeTotal = notasState.sumOf { it.porcentaje ?: 0.0 }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(asignatura.nombre ?: "Asignatura") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        notaEnEdicion = null
                        showDialog = true
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Anadir nota")
                    }
                    IconButton(onClick = {
                        infoMessage = "Gestiona notas con porcentaje por periodo."
                    }) {
                        Icon(Icons.Default.Info, contentDescription = "Informacion")
                    }
                    IconButton(onClick = { showDeleteAsignatura = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Eliminar asignatura",
                            tint = MaterialTheme.colorScheme.error,
                        )
                    }
                },
            )
        },
    ) { inner ->
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                EncabezadoNotas(
                    nombre = asignatura.nombre ?: "",
                    promedio = promedio,
                    porcentajeTotal = porcentajeTotal,
                )
                infoMessage?.let { message ->
                    Text(text = message, color = MaterialTheme.colorScheme.primary)
                }
                ListaNotasPorPeriodo(
                    notas = notasState,
                    numeroPeriodos = asignatura.numero_periodos ?: 3,
                    tipoPeriodo = asignatura.tipo_periodo ?: "Trimestre",
                    onEditar = { nota ->
                        notaEnEdicion = nota
                        showDialog = true
                    },
                    onEliminar = { nota ->
                        showDeleteConfirm = nota
                    },
                )
            }
        }
    }

    if (showDialog) {
        NotaDialog(
            numeroPeriodos = asignatura.numero_periodos ?: 3,
            nota = notaEnEdicion,
            onDismiss = { showDialog = false },
            onSave = { nota ->
                onSaveNota(nota)
                showDialog = false
            },
            notasActuales = notasState,
        )
    }

    showDeleteConfirm?.let { nota ->
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = null },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteNota(nota)
                        showDeleteConfirm = null
                    },
                ) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = null }) { Text("Cancelar") }
            },
            title = { Text("Eliminar nota") },
            text = { Text("Seguro que deseas eliminar ${nota.nombre}?") },
        )
    }

    if (showDeleteAsignatura) {
        AlertDialog(
            onDismissRequest = { showDeleteAsignatura = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteAsignatura()
                        showDeleteAsignatura = false
                    },
                ) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAsignatura = false }) { Text("Cancelar") }
            },
            title = { Text("Eliminar asignatura") },
            text = { Text("Deseas eliminar toda la asignatura y sus notas?") },
        )
    }
}

@Composable
private fun EncabezadoNotas(nombre: String, promedio: Double, porcentajeTotal: Double) {
    BoxWithConstraints {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
                Box(
                    modifier = Modifier
                        .size(screenHeight * 0.2f)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = abreviarNombre(nombre),
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Media", style = MaterialTheme.typography.labelMedium)
                        Text(text = formatMedia(promedio), style = MaterialTheme.typography.titleMedium)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Porcentaje usado", style = MaterialTheme.typography.labelMedium)
                        Row(
                            modifier = Modifier.width(screenWidth * 0.25f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                modifier = Modifier.padding(top = screenHeight * 0.015f, start = screenWidth * 0.05f),
                                text = formatPercent(porcentajeTotal),
                                style = MaterialTheme.typography.titleMedium,
                            )
                            if (porcentajeTotal > 100) {
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.Info, contentDescription = "Informacion")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ListaNotasPorPeriodo(
    notas: List<Notas>,
    numeroPeriodos: Int,
    tipoPeriodo: String,
    onEditar: (Notas) -> Unit,
    onEliminar: (Notas) -> Unit,
) {
    val grouped = notas.groupBy { it.periodo ?: 1 }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 8.dp),
    ) {
        (1..numeroPeriodos).forEach { periodo ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                ) {
                    Text(
                        text = "$tipoPeriodo $periodo",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 12.dp),
                    )
                }
            }
            val lista = grouped[periodo].orEmpty()
            items(lista) { nota ->
                NotaRow(nota, onEditar = { onEditar(nota) }, onEliminar = { onEliminar(nota) })
                Divider()
            }
            item { Spacer(modifier = Modifier.height(4.dp)) }
        }
    }
}

@Composable
private fun NotaRow(nota: Notas, onEditar: () -> Unit, onEliminar: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onEditar() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = nota.nombre ?: "Examen", style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = nota.fecha.orEmpty(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.padding(end = 8.dp)) {
                Text(text = formatDecimal1(nota.nota ?: 0.0), fontWeight = FontWeight.Bold)
                Text(text = "${nota.porcentaje ?: 0.0}%", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onEditar) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
private fun NotaDialog(
    numeroPeriodos: Int,
    nota: Notas?,
    onDismiss: () -> Unit,
    onSave: (Notas) -> Unit,
    notasActuales: List<Notas>,
) {
    val nombre = remember { mutableStateOf(nota?.nombre ?: "") }
    val fecha = remember { mutableStateOf(nota?.fecha ?: "") }
    val notaValor = remember { mutableStateOf((nota?.nota ?: 0.0).toString()) }
    val porcentaje = remember { mutableStateOf((nota?.porcentaje ?: 0.0).toString()) }
    val periodo = remember { mutableStateOf(nota?.periodo ?: 1) }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (nota == null) "Agregar nota" else "Editar nota") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = nombre.value, onValueChange = { nombre.value = it }, label = { Text("Nombre del examen") })
                OutlinedTextField(
                    value = fecha.value,
                    onValueChange = { fecha.value = it },
                    label = { Text("Fecha (dd/MM/yyyy)") },
                )
                OutlinedTextField(
                    value = notaValor.value,
                    onValueChange = { notaValor.value = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Nota (0-10)") },
                )
                OutlinedTextField(
                    value = porcentaje.value,
                    onValueChange = { porcentaje.value = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Porcentaje (0-100)") },
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Periodo:")
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        for (p in 1..numeroPeriodos) {
                            val selected = periodo.value == p
                            Button(
                                onClick = { periodo.value = p },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                ),
                            ) { Text("$p") }
                        }
                    }
                }
                if (error != null) {
                    Text(text = error.orEmpty(), color = MaterialTheme.colorScheme.error)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val notaDouble = notaValor.value.toDoubleOrNull() ?: -1.0
                val porcentajeDouble = porcentaje.value.toDoubleOrNull() ?: -1.0
                if (nombre.value.isBlank() || notaDouble < 0 || porcentajeDouble <= 0) {
                    error = "Completa los campos correctamente"
                    return@TextButton
                }
                val porcentajeUsado = notasActuales
                    .filter { it.periodo == periodo.value && it.id != nota?.id }
                    .sumOf { it.porcentaje ?: 0.0 }
                if (porcentajeUsado + porcentajeDouble > 100.0 + 1e-6) {
                    error = "El porcentaje total del periodo supera 100%"
                    return@TextButton
                }
                val notaFinal = Notas(
                    id = nota?.id ?: "",
                    id_asignatura = nota?.id_asignatura,
                    nombre = nombre.value,
                    nota = notaDouble,
                    porcentaje = porcentajeDouble,
                    fecha = fecha.value,
                    periodo = periodo.value,
                )
                onSave(notaFinal)
            }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
    )
}

@Composable
fun PerfilScreen(
    user: Usuario?,
    anios: List<Anio>,
    onBack: () -> Unit,
    onSave: (String, String) -> Unit,
    onDeleteAccount: () -> Unit,
    onLogout: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var nombreEditable by remember(user) { mutableStateOf(user?.nombre ?: "") }
    var passwordEditable by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil de usuario") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atras")
                    }
                },
                actions = {
                    IconButton(onClick = { onSave(nombreEditable, passwordEditable) }) {
                        Icon(Icons.Default.Save, contentDescription = "Guardar")
                    }
                },
            )
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            if (user == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            } else {
                BoxWithConstraints {
                    val screenHeight = maxHeight
                    val horizontalPadding = 24.dp
                    val verticalPadding = 16.dp

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontalPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(verticalPadding))
                            Surface(
                                modifier = Modifier
                                    .size(screenHeight * 0.15f)
                                    .clip(CircleShape),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                tonalElevation = 3.dp,
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Foto de perfil",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(screenHeight * 0.08f),
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(verticalPadding / 2))
                            Text(
                                user.nombre ?: "",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Text(
                                user.email ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                            Spacer(modifier = Modifier.height(verticalPadding))
                        }

                        item {
                            Text(
                                text = "Informacion de la cuenta",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                            )
                        }
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = MaterialTheme.shapes.extraLarge,
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            ) {
                                Column(modifier = Modifier.padding(horizontalPadding / 2)) {
                                    OutlinedTextField(
                                        value = nombreEditable,
                                        onValueChange = { nombreEditable = it },
                                        label = { Text("Nombre") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 12.dp),
                                    )
                                    OutlinedTextField(
                                        value = user.email ?: "",
                                        onValueChange = { },
                                        label = { Text("Email") },
                                        readOnly = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 12.dp),
                                    )
                                    PasswordTextField(
                                        value = passwordEditable,
                                        onValueChange = { passwordEditable = it },
                                        label = { Text("Nueva contrasena (opcional)") },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 12.dp, bottom = 16.dp),
                                    )
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(verticalPadding))
                            Text(
                                text = "Anios escolares",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                            )
                        }
                        if (anios.isNotEmpty()) {
                            items(anios) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 8.dp),
                                    shape = MaterialTheme.shapes.large,
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                ) {
                                    ListItem(
                                        headlineContent = { Text(it.nombre ?: "") },
                                    )
                                }
                            }
                        } else {
                            item {
                                Text(
                                    "No hay anios escolares registrados.",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(verticalPadding))
                            OutlinedButton(
                                onClick = { showDeleteDialog = true },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.error,
                                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.12f),
                                ),
                            ) {
                                Text("Eliminar cuenta")
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { onLogout() },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary,
                                ),
                            ) {
                                Text("Cerrar sesion")
                            }
                            Spacer(modifier = Modifier.height(verticalPadding))
                        }
                    }
                }
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Eliminar tu cuenta?") },
                text = {
                    Text("Esta accion es permanente. Se borraran todos tus datos.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDeleteDialog = false
                            onDeleteAccount()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError,
                        ),
                    ) {
                        Text("Si, eliminar todo")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancelar")
                    }
                },
            )
        }
    }
}

@Composable
private fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit),
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier,
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordVisible) "Ocultar contrasena" else "Mostrar contrasena"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = description)
            }
        },
    )
}
