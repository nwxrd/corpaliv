package cl.duoc.corpaliv_grupo9

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.corpaliv_grupo9.navigation.Screen
import cl.duoc.corpaliv_grupo9.ui.components.NavBar
import cl.duoc.corpaliv_grupo9.ui.screens.CatalogoScreen
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Corpaliv_Grupo9Theme {
                CorpalivApp()          // ← toda la app está en esta función
            }
        }
    }
}

@Composable
fun CorpalivApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { NavBar(navController) }          // ← barra de abajo
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Catalogo.route,  // ← parte en Catálogo mientras pruebas
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { PantallaPendiente("Inicio") }
            composable(Screen.Info.route) { PantallaPendiente("Info") }
            composable(Screen.Catalogo.route) {
                CatalogoScreen(onCotizar = { producto ->
                    Toast.makeText(context, "Cotizar: ${producto.nombre}", Toast.LENGTH_SHORT).show()
                })
            }
            composable(Screen.Formulario.route) { PantallaPendiente("Formulario") }
            composable(Screen.Panel.route) { PantallaPendiente("Panel") }
        }
    }
}

// Pantalla temporal para las que aún no existen
@Composable
fun PantallaPendiente(nombre: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("$nombre — en construcción")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CorpalivAppPreview() {
    Corpaliv_Grupo9Theme {
        CorpalivApp()
    }
}