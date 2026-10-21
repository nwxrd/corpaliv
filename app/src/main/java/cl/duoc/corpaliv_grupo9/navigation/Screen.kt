package cl.duoc.corpaliv_grupo9.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Info : Screen("info")
    object Catalogo : Screen("catalogo")
    object Formulario : Screen("formulario")
    object Confirmacion : Screen("confirmacion")
    object Panel : Screen("panel")
}