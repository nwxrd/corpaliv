package cl.duoc.corpaliv_grupo9.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.corpaliv_grupo9.ui.components.BackButton
import cl.duoc.corpaliv_grupo9.ui.components.Input
import cl.duoc.corpaliv_grupo9.ui.components.SubmitButton
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme

@Composable
fun LoginScreen() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- 1. LOGO DE CANDADO ---
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Logo Candado",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ingresa tus credenciales",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Panel de usuario",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Input(
            value = email,
            onValueChange = { email = it },
            label = "Correo electrónico",
            placeholder = "correo@dominio.cl",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Input(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            placeholder = "********",
            isPassword = true,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        SubmitButton(
            text = "Ingresar",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(12.dp))

        BackButton(
            text = "Volver al inicio",
            onClick = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    Corpaliv_Grupo9Theme {
        Surface {
            LoginScreen()
        }
    }
}