package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.duoc.corpaliv_grupo9.navigation.Screen
import cl.duoc.corpaliv_grupo9.navigation.pantallasAdmin
import cl.duoc.corpaliv_grupo9.navigation.pantallasDrawer
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme

@Composable
fun AppDrawer(
    currentRoute: String?,
    items: List<Screen>,
    adminItems: List<Screen>,
    onItemClick: (Screen) -> Unit
) {
    ModalDrawerSheet {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Corpaliv",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        items.forEach { item ->
            DrawerDestination(
                item = item,
                selected = currentRoute == item.route,
                onClick = { onItemClick(item) }
            )
        }

        if (adminItems.isNotEmpty()) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            adminItems.forEach { item ->
                DrawerDestination(
                    item = item,
                    selected = currentRoute == item.route,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
private fun DrawerDestination(
    item: Screen,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(item.title) },
        selected = selected,
        onClick = onClick,
        icon = { Icon(item.icon!!, contentDescription = item.title) },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

@Preview(showBackground = true)
@Composable
fun AppDrawerPreview() {
    Corpaliv_Grupo9Theme {
        AppDrawer(
            currentRoute = null,
            items = pantallasDrawer(),
            adminItems = pantallasAdmin(),
            onItemClick = {}
        )
    }
}
