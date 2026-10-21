package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import cl.duoc.corpaliv_grupo9.navigation.Screen
import cl.duoc.corpaliv_grupo9.ui.theme.Brand600

data class NavItem(val screen: Screen, val etiqueta: String, val icono: ImageVector)


private val itemsNav = listOf(
    NavItem(Screen.Home,"Inicio", Icons.Filled.Home),
    NavItem(Screen.Info,"Info", Icons.Filled.Info),
    NavItem(Screen.Catalogo,"Catalogo", Icons.Filled.Storefront),
    NavItem(Screen.Home,"Registro", Icons.Filled.EditNote),
    NavItem(Screen.Home,"Panel", Icons.AutoMirrored.Filled.ShowChart)
)
@Composable
fun NavBar(navController: NavHostController){
    val backStack by navController.currentBackStackEntryAsState()
    val rutaActual = backStack?.destination?.route

    NavigationBar(containerColor = Color.White) {
        itemsNav.forEach { item ->
            NavigationBarItem( selected =rutaActual == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route){
                        popUpTo(navController.graph.findStartDestination().id){saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon={ Icon(item.icono, contentDescription = item.etiqueta)},
                label={Text(item.etiqueta, fontSize =10.sp)},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Brand600,
                    selectedTextColor = Brand600,
                    unselectedIconColor = Color(0xFF94A3B8),
                    unselectedTextColor = Color(0xFF94A3B8),
                    indicatorColor = Color(0xFFE0F2FE)

                )

            )

        }
    }

}

