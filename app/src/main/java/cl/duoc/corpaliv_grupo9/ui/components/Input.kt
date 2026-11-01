package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme

@Composable
fun Input(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    leadingIconImage: ImageVector? = null
) {
    var passwordVisible by rememberSaveable() { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = placeholder)
            },
            leadingIcon = {
                leadingIconImage?.let { icon ->
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = {
                if (isPassword) {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputPreview(){
    Corpaliv_Grupo9Theme {
        var correoPrueba by rememberSaveable { mutableStateOf("") }
        var passPrueba by rememberSaveable { mutableStateOf("") }

        Surface(modifier = Modifier.padding(16.dp)) {
            Column {
                Input(
                    value = correoPrueba,
                    onValueChange = { correoPrueba = it },
                    label = "Correo electrónico",
                    placeholder = "nombre@corpaliv.test",
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Input(
                    value = passPrueba,
                    onValueChange = { passPrueba = it },
                    label = "Contraseña",
                    placeholder = "Mínimo 8 caracteres",
                    isPassword = true
                )
            }
        }
    }
}