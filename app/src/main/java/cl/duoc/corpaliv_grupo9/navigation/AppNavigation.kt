package cl.duoc.corpaliv_grupo9.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import cl.duoc.corpaliv_grupo9.R
import cl.duoc.corpaliv_grupo9.data.CorpalivDatabase
import cl.duoc.corpaliv_grupo9.model.TipoColaboracion
import cl.duoc.corpaliv_grupo9.ui.components.*
import cl.duoc.corpaliv_grupo9.ui.screens.*
import cl.duoc.corpaliv_grupo9.ui.theme.corpalivSurfacePalette
import cl.duoc.corpaliv_grupo9.viewmodel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val palette = corpalivSurfacePalette()
    val navController = rememberNavController()
    val context = LocalContext.current
    val database = remember(context) { CorpalivDatabase.getInstance(context) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isAdminAuthenticated by remember { mutableStateOf(false) }

    val registroViewModel: RegistroViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()
    val perfilViewModel: PerfilViewModel = viewModel()

    val backStack by navController.currentBackStackEntryAsState()
    val rutaActual = backStack?.destination?.route
    val enLogin = rutaActual == Screen.Login.route
    val enPanelAdmin = rutaActual == Screen.AdminPanel.route
    val mostrarLogo = rutaActual in setOf(
        Screen.Home.route,
        Screen.Info.route,
        Screen.Catalogo.route,
        Screen.Registro.route
    )

    val destinosBarra = pantallasBarra()
    val destinosDrawer = pantallasDrawer()
    val destinosAdmin = if (isAdminAuthenticated) {
        listOf(Screen.AdminPanel)
    } else {
        pantallasAdmin()
    }

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
            containerColor = palette.background,
            contentColor = palette.text,
            topBar = {
                if (!enLogin) {
                    val menuButton: @Composable () -> Unit = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open()
                                    else drawerState.close()
                                }
                            }
                        ) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menú")
                        }
                    }
                    val appBarColors = TopAppBarDefaults.topAppBarColors(
                        containerColor = palette.surface,
                        titleContentColor = palette.text,
                        navigationIconContentColor = palette.text,
                        actionIconContentColor = palette.text
                    )

                    if (mostrarLogo || enPanelAdmin) {
                        CenterAlignedTopAppBar(
                            title = {
                                Box(
                                    modifier = Modifier
                                        .width(184.dp)
                                        .height(52.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.White)
                                        .padding(horizontal = 3.dp, vertical = 3.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.corpaliv_logo),
                                        contentDescription = "CORPALIV",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            },
                            colors = appBarColors,
                            navigationIcon = menuButton,
                            actions = {
                                if (enPanelAdmin) {
                                    AdminAvatarAction(
                                        perfilViewModel = perfilViewModel,
                                        modifier = Modifier.padding(end = 4.dp)
                                    )
                                }
                            }
                        )
                    } else {
                        TopAppBar(
                            title = { Text(titleFor(rutaActual)) },
                            colors = appBarColors,
                            navigationIcon = menuButton
                        )
                    }
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
            AppNavHost(
                navController = navController,
                registroViewModel = registroViewModel,
                carritoViewModel = carritoViewModel,
                perfilViewModel = perfilViewModel,
                database = database,
                isAdminAuthenticated = isAdminAuthenticated,
                onLoginSuccess = {
                    isAdminAuthenticated = true
                    navController.navigate(Screen.AdminPanel.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onLogout = {
                    isAdminAuthenticated = false
                    navController.navigateTo(Screen.Home.route)
                },
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun AppNavHost(
    navController: NavHostController,
    registroViewModel: RegistroViewModel,
    carritoViewModel: CarritoViewModel,
    perfilViewModel: PerfilViewModel,
    database: CorpalivDatabase,
    isAdminAuthenticated: Boolean,
    onLoginSuccess: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLogin = { username, password ->
                    withContext(Dispatchers.IO) {
                        database.verifyCredentials(username, password)
                    }
                },
                onLoginSuccess = onLoginSuccess,
                onVolverAlInicio = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.AdminPanel.route) {
            if (isAdminAuthenticated) {
                AdminPanelScreen(
                    onCerrarSesion = onLogout,
                    perfilViewModel = perfilViewModel
                )
            } else {
                LaunchedEffect(Unit) {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.AdminPanel.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigate = { route -> navController.navigateTo(route) }
            )
        }

        composable(Screen.Info.route) {
            InfoScreen(
                onNavigateToCatalog = {
                    navController.navigateTo(Screen.Catalogo.route)
                },
                onRegisterClick = {
                    navController.navigateTo(Screen.Registro.route)
                }
            )
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

private fun NavHostController.navigateTo(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id)
        launchSingleTop = true

    }
}
