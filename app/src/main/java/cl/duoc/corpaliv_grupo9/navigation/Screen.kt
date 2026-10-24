package cl.duoc.corpaliv_grupo9.navigation

sealed class Screen(val route: String) {
    // Pantallas 1-4 (Julián)
    object Login : Screen("login")
    object Home : Screen("home")
    object Info : Screen("info")
    object Casos : Screen("casos")

    // Pantallas 5-8 (Eduardo)
    object Catalogo : Screen("catalogo")
    object Registro : Screen("formulario")
    object Confirmacion : Screen("confirmacion")
    object Panel : Screen("panel")
}