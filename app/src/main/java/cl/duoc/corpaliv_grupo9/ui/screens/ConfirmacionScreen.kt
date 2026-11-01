package cl.duoc.corpaliv_grupo9.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.corpaliv_grupo9.R
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme
import cl.duoc.corpaliv_grupo9.ui.theme.Green100
import cl.duoc.corpaliv_grupo9.ui.theme.Green400
import cl.duoc.corpaliv_grupo9.ui.theme.Green600
import cl.duoc.corpaliv_grupo9.ui.theme.Green950

@Composable
fun ConfirmacionScreen(
    onVolverInicio:()-> Unit
) {
    val colores = MaterialTheme.colorScheme
    val modoOscuro = isSystemInDarkTheme()
    val fondoCheck = if (modoOscuro) Green950 else Green100
    val colorCheck = if (modoOscuro) Green400 else Green600
    var mostrarCheck by rememberSaveable{ mutableStateOf(false) }
    LaunchedEffect(Unit) {mostrarCheck = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colores.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.corpaliv_logo),
                contentDescription = "Logo Corpaliv",
                contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(32.dp) )

            AnimatedVisibility(
                visible = mostrarCheck,
                enter = scaleIn()+fadeIn()
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(fondoCheck, CircleShape),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Registro Exitoso",
                        tint = colorCheck,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }


            Spacer(modifier =  Modifier.height(12.dp)  )

            Text(
                text =" Gracias  por tu interés en sumarte a CORPARLIV " +
                        "Nuestro equipo de vinculacion se pondrá en contacto pronto. ",
                fontSize= 14.sp,
                color = colores.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        // volver a inicio
        Button(
            onClick = onVolverInicio,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colores.primary,
                contentColor = colores.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)

        ){
            Text(" Volver al inicio ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }

}
@Preview(showBackground = true)
@Composable
fun ConfirmacionScreenPreview() {
    Corpaliv_Grupo9Theme {
        ConfirmacionScreen(onVolverInicio = {})
    }
}