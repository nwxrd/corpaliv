package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.corpaliv_grupo9.R
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
    val colors = MaterialTheme.colorScheme

    ModalDrawerSheet(
        modifier = Modifier.widthIn(max = 320.dp),
        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
        drawerContainerColor = colors.background,
        drawerTonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Image(
                    painter = painterResource(R.drawable.corpaliv_logo),
                    contentDescription = "CORPALIV",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(150.dp)
                        .height(48.dp)
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "Red Alba Lab",
                    color = colors.onSurfaceVariant,
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
                color = colors.onSurfaceVariant.copy(alpha = 0.16f),
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
    val colors = MaterialTheme.colorScheme

    Text(
        text = text,
        color = colors.onSurfaceVariant,
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
    val colors = MaterialTheme.colorScheme

    NavigationDrawerItem(
        label = { Text(item.title) },
        selected = selected,
        onClick = onClick,
        icon = { Icon(item.icon!!, contentDescription = item.title) },
        shape = RoundedCornerShape(14.dp),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = colors.primaryContainer,
            selectedIconColor = colors.primary,
            selectedTextColor = colors.primary,
            unselectedContainerColor = Color.Transparent,
            unselectedIconColor = colors.onSurfaceVariant,
            unselectedTextColor = colors.onSurface
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
