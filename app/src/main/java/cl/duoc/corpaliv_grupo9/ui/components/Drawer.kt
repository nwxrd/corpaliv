package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.corpaliv_grupo9.navigation.Screen
import cl.duoc.corpaliv_grupo9.navigation.pantallasAdmin
import cl.duoc.corpaliv_grupo9.navigation.pantallasDrawer
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme
import cl.duoc.corpaliv_grupo9.ui.theme.HomePrimary
import cl.duoc.corpaliv_grupo9.ui.theme.White
import cl.duoc.corpaliv_grupo9.ui.theme.corpalivSurfacePalette

@Composable
fun AppDrawer(
    currentRoute: String?,
    items: List<Screen>,
    adminItems: List<Screen>,
    onItemClick: (Screen) -> Unit
) {
    val palette = corpalivSurfacePalette()

    ModalDrawerSheet(
        modifier = Modifier.widthIn(max = 320.dp),
        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
        drawerContainerColor = palette.background,
        drawerTonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = CircleShape,
                color = HomePrimary
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("C", color = White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                }
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = "CORPALIV",
                    color = palette.text,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Red Alba Lab",
                    color = palette.secondaryText,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        DrawerGroupLabel("TU ESPACIO")

        items.forEach { item ->
            DrawerDestination(
                item = item,
                selected = currentRoute == item.route,
                onClick = { onItemClick(item) }
            )
        }

        if (adminItems.isNotEmpty()) {
            HorizontalDivider(
                color = palette.secondaryText.copy(alpha = 0.16f),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
            DrawerGroupLabel("ADMINISTRACIÓN")
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
private fun DrawerGroupLabel(text: String) {
    val palette = corpalivSurfacePalette()

    Text(
        text = text,
        color = palette.secondaryText,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 28.dp, top = 8.dp, bottom = 6.dp)
    )
}

@Composable
private fun DrawerDestination(
    item: Screen,
    selected: Boolean,
    onClick: () -> Unit
) {
    val palette = corpalivSurfacePalette()

    NavigationDrawerItem(
        label = { Text(item.title) },
        selected = selected,
        onClick = onClick,
        icon = { Icon(item.icon!!, contentDescription = item.title) },
        shape = RoundedCornerShape(14.dp),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = palette.cardBlue,
            selectedIconColor = palette.primary,
            selectedTextColor = palette.primary,
            unselectedContainerColor = Color.Transparent,
            unselectedIconColor = palette.secondaryText,
            unselectedTextColor = palette.text
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
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
