package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SubmitButton(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text("Enviar Solicitud")
    }
}


@Preview(showBackground = true)
@Composable
fun SubmitButtonPreview(){
    SubmitButton(onClick = {})
}