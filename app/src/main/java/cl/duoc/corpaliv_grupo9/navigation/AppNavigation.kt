package cl.duoc.corpaliv_grupo9.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import cl.duoc.corpaliv_grupo9.model.TipoColaboracion
import cl.duoc.corpaliv_grupo9.ui.components.*
import cl.duoc.corpaliv_grupo9.ui.screens.*
import cl.duoc.corpaliv_grupo9.viewmodel.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val registroViewModel: RegistroViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()

    val backStack by navController.currentBackStackEntryAsState()
    val rutaActual = backStack?.destination?.route
    val enLogin = rutaActual == Screen.Login.route

    val destinosBarra = pantallasBarra()
    val destinosDrawer = pantallasDrawer()
    val destinosAdmin = pantallasAdmin()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = !enLogin,
        drawerContent = {
            AppDrawer(
                currentRoute = rutaActual,
                items = destinosDrawer,
                adminItems = destinosAdmin,
                onItemClick = { screen ->
                    scope.launch { drawerState.close() }
                    if (screen == Screen.Login) {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigateTo(screen.route)
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                if (!enLogin) {
                    TopAppBar(
                        title = { Text(titleFor(rutaActual)) },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menú")
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (!enLogin) {
                    NavBar(
                        items = destinosBarra,
                        currentRoute = rutaActual,
                        onItemClick = { navController.navigateTo(it.route) }
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {

                composable(Screen.Login.route) {
                    LoginScreen(
                        onVolverAlInicio = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable(Screen.Home.route) {
                    HomeScreen()
                }

                composable(Screen.Info.route) {
                    InfoScreen()
                }

                composable(Screen.Casos.route) {
                    Text("Casos de Impacto")
                }

                composable(Screen.Catalogo.route) {
                    CatalogoScreen(
                        carritoViewModel = carritoViewModel,
                        onFinalizarCompra = {
                            registroViewModel.onTipoChange(TipoColaboracion.Comprador)
                            navController.navigate(Screen.Registro.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }

                composable(Screen.Registro.route) {
                    RegistroScreen(
                        viewModel = registroViewModel,
                        carritoViewModel = carritoViewModel,
                        onRegistroExitoso = {
                            navController.navigate(Screen.Confirmacion.route) {
                                launchSingleTop = true
                            }
                        },
                        onVerPedido = {
                            carritoViewModel.abrirPedido()
                            navController.navigateTo(Screen.Catalogo.route)
                        }
                    )
                }

                composable(Screen.Confirmacion.route) {
                    ConfirmacionScreen(
                        onVolverInicio = { navController.navigateTo(Screen.Home.route) }
                    )
                }
            }
        }
    }
}

private fun NavHostController.navigateTo(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id)
        launchSingleTop = true

    }
}
