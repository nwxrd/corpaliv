package cl.duoc.corpaliv_grupo9.ui.screens

import android.database.sqlite.SQLiteException
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.corpaliv_grupo9.ui.components.Input
import cl.duoc.corpaliv_grupo9.ui.theme.AdminHeroEnd
import cl.duoc.corpaliv_grupo9.ui.theme.AdminHeroDeepBlue
import cl.duoc.corpaliv_grupo9.ui.theme.AdminHeroMint
import cl.duoc.corpaliv_grupo9.ui.theme.AdminHeroShape
import cl.duoc.corpaliv_grupo9.ui.theme.AdminHeroStart
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme
import cl.duoc.corpaliv_grupo9.ui.theme.HomePrimary
import cl.duoc.corpaliv_grupo9.ui.theme.White
import kotlinx.coroutines.launch

private const val LOGIN_HERO_HEIGHT = 350
private const val LOGIN_FORM_OVERLAP = 28

@Composable
fun LoginScreen(
    onVolverAlInicio: () -> Unit,
    onLogin: suspend (String, String) -> Boolean,
    onLoginSuccess: () -> Unit
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val colors = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surface)
    ) {
        LoginHero(onBack = onVolverAlInicio)
        LoginFormContent(
            username = username,
            password = password,
            onUsernameChange = {
                username = it
                errorMessage = null
            },
            onPasswordChange = { password = it },
            isSubmitting = isSubmitting,
            errorMessage = errorMessage,
            onSubmit = {
                if (username.isBlank() || password.isEmpty()) {
                    errorMessage = "Ingresa tu usuario y contraseña."
                } else {
                    scope.launch {
                        isSubmitting = true
                        errorMessage = null
                        try {
                            if (onLogin(username.trim(), password)) {
                                password = ""
                                onLoginSuccess()
                            } else {
                                errorMessage = "Usuario o contraseña incorrectos."
                            }
                        } catch (_: SQLiteException) {
                            errorMessage = "No se pudo consultar la base de datos."
                        } finally {
                            isSubmitting = false
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = (LOGIN_HERO_HEIGHT - LOGIN_FORM_OVERLAP).dp)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(colors.background)
        )
    }
}

@Composable
private fun LoginFormContent(
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    isSubmitting: Boolean,
    errorMessage: String?,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Input(
            value = username,
            onValueChange = onUsernameChange,
            label = "Usuario",
            placeholder = "admin",
            leadingIconImage = Icons.Default.Person,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Input(
            value = password,
            onValueChange = onPasswordChange,
            label = "Contraseña",
            placeholder = "Contraseña",
            isPassword = true,
            leadingIconImage = Icons.Default.Lock,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            onClick = onSubmit,
            enabled = !isSubmitting,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.primary,
                contentColor = colors.onPrimary,
                disabledContainerColor = colors.primary,
                disabledContentColor = colors.onPrimary
            ),
            contentPadding = PaddingValues(start = 20.dp, end = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isSubmitting) "Validando..." else "Ingresar",
                    color = White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(colors.inverseSurface, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = colors.inverseOnSurface,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        errorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        Text(
            text = "Acceso solo para el panel de administración.",
            color = colors.onSurfaceVariant,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Composable
private fun LoginHero(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(LOGIN_HERO_HEIGHT.dp)
            .background(
                Brush.linearGradient(listOf(AdminHeroStart, AdminHeroEnd))
            )
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = AdminHeroShape.copy(alpha = 0.18f),
                radius = size.width * 0.34f,
                center = Offset(size.width * 0.86f, size.height * 0.12f)
            )
            drawCircle(
                color = White.copy(alpha = 0.16f),
                radius = size.width * 0.105f,
                center = Offset(size.width * 0.74f, size.height * 0.75f)
            )
            drawCircle(
                color = AdminHeroDeepBlue.copy(alpha = 0.3f),
                radius = size.width * 0.33f,
                center = Offset(-size.width * 0.04f, size.height * 0.68f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(48.dp)
                    .background(White.copy(alpha = 0.17f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = White
                )
            }
            Spacer(Modifier.height(22.dp))

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .background(White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = HomePrimary,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(Modifier.height(18.dp))
            Text(
                text = "ACCESO ADMINISTRADOR",
                color = AdminHeroMint,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = "Ingresa tus credenciales",
                color = White,
                fontSize = 25.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Panel de usuario",
                color = White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    Corpaliv_Grupo9Theme {
        Surface {
            LoginScreen(
                onVolverAlInicio = {},
                onLogin = { _, _ -> false },
                onLoginSuccess = {}
            )
        }
    }
}