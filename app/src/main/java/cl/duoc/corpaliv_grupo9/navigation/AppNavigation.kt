package cl.duoc.corpaliv_grupo9.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cl.duoc.corpaliv_grupo9.ui.components.NavBar
import cl.duoc.corpaliv_grupo9.ui.screens.CatalogoScreen
import cl.duoc.corpaliv_grupo9.ui.screens.RegistroScreen
import cl.duoc.corpaliv_grupo9.viewmodel.RegistroViewModel
import cl.duoc.corpaliv_grupo9.model.TipoColaboracion
import cl.duoc.corpaliv_grupo9.viewmodel.CarritoViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()


    val registroViewModel: RegistroViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()

    // Ruta actual: sirve para ocultar la barra de abajo en el Login
    val backStack by navController.currentBackStackEntryAsState()
    val rutaActual = backStack?.destination?.route

    Scaffold(
        bottomBar = {
            if (rutaActual != Screen.Login.route) {
                NavBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = //Screen.Login.route,
            Screen.Catalogo.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            // ================= Pantallas 1-4 (Julián) =================

            // 1. Login / Acceso
            composable(Screen.Login.route) {
                Text("Login")   // 👈 Julián: reemplazar por LoginScreen(...)
                // Al ingresar, ir a Home y sacar el Login del historial:
                // navController.navigate(Screen.Home.route) {
                //     popUpTo(Screen.Login.route) { inclusive = true }
                // }
            }

            // 2. Inicio / Home
            composable(Screen.Home.route) {
                Text("Inicio")  //  reemplazar por HomeScreen(...)
            }

            // 3. Info CORPALIV (Swipe)
            composable(Screen.Info.route) {
                Text("Info CORPALIV")  //  reemplazar por InfoScreen(...)
            }

            // 4. Casos de Impacto
            composable(Screen.Casos.route) {
                Text("Casos de Impacto")  // reemplazar por CasosScreen(...)
            }

            // ================= Pantallas 5-8 (Eduardo) =================

            // 5. Catálogo Alba Lab
            composable(Screen.Catalogo.route) {
                CatalogoScreen(
                    carritoViewModel = carritoViewModel,
                    onFinalizarCompra = {
                        // Deja elegido "Compra de Productos Alba Lab" y va al formulario
                        registroViewModel.onTipoChange(TipoColaboracion.Comprador)
                        navController.navigate(Screen.Registro.route)
                    }
                )
            }

            // 6. Formulario Interés
            composable(Screen.Registro.route) {
                RegistroScreen(
                    viewModel = registroViewModel,
                    carritoViewModel = carritoViewModel,
                    onRegistroExitoso = { navController.navigate(Screen.Confirmacion.route) }
                )
            }

            // 7. Confirmación
            composable(Screen.Confirmacion.route) {
                Text("¡Registro Recibido!")  //  pendiente: ConfirmacionScreen(...)
            }

            // 8. Panel Vinculación
            composable(Screen.Panel.route) {
                Text("Panel de Vinculación")  //  pendiente: PanelScreen(...)
            }
        }
    }
}

