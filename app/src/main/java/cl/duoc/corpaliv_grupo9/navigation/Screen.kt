package cl.duoc.corpaliv_grupo9.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    data object Login : Screen("login", "Acceso administrador", Icons.Filled.Lock)
    data object Home : Screen("home", "Inicio", Icons.Filled.Home)
    data object Info : Screen("info", "Info", Icons.Filled.Info)
    data object Casos : Screen("casos", "Casos", Icons.Filled.VolunteerActivism)
    data object Catalogo : Screen("catalogo", "Catálogo", Icons.Filled.Storefront)
    data object Registro : Screen("formulario", "Registro", Icons.Filled.EditNote)
    data object Confirmacion : Screen("confirmacion", "Confirmación")
}

fun pantallasBarra(): List<Screen> =
    listOf(Screen.Home, Screen.Info, Screen.Catalogo, Screen.Registro)

fun pantallasDrawer(): List<Screen> = listOf(Screen.Registro)

fun pantallasAdmin(): List<Screen> = listOf(Screen.Login)

fun titleFor(route: String?): String {
    return listOf(
        Screen.Login,
        Screen.Home,
        Screen.Info,
        Screen.Casos,
        Screen.Catalogo,
        Screen.Registro,
        Screen.Confirmacion
    ).find { it.route == route }?.title ?: Screen.Home.title
}
