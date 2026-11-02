package cl.duoc.corpaliv_grupo9.ui.screens

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import cl.duoc.corpaliv_grupo9.ui.theme.AdminHeroShape
import cl.duoc.corpaliv_grupo9.ui.theme.AdminHeroStart
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme
import cl.duoc.corpaliv_grupo9.ui.theme.HomePrimary
import cl.duoc.corpaliv_grupo9.ui.theme.White
import cl.duoc.corpaliv_grupo9.ui.theme.corpalivSurfacePalette

private const val LOGIN_HERO_HEIGHT = 350
private const val LOGIN_FORM_OVERLAP = 28

@Composable
fun LoginScreen(
    onVolverAlInicio: () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val palette = corpalivSurfacePalette()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.background)
    ) {
        LoginHero(onBack = onVolverAlInicio)
        LoginFormContent(
            email = email,
            password = password,
            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            modifier = Modifier
                .fillMaxSize()
                .padding(top = (LOGIN_HERO_HEIGHT - LOGIN_FORM_OVERLAP).dp)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(palette.formSurface)
        )
    }
}

@Composable
private fun LoginFormContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val palette = corpalivSurfacePalette()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        Input(
            value = email,
            onValueChange = onEmailChange,
            label = "Correo electrónico",
            placeholder = "correo@dominio.cl",
            leadingIconImage = Icons.Default.Email,
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
            onClick = {},
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = HomePrimary),
            contentPadding = PaddingValues(start = 20.dp, end = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ingresar",
                    color = White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = HomePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        Text(
            text = "Autenticación aún no conectada en esta maqueta.",
            color = palette.secondaryText,
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
                color = Color(0xFF102FA4).copy(alpha = 0.3f),
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
                color = Color(0xFF82E8E1),
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
            LoginScreen(onVolverAlInicio = {})
        }
    }
}