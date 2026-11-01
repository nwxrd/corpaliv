package cl.duoc.corpaliv_grupo9.model
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class AporteItem(
    val tag: String,
    val title: String,
    val icon: ImageVector,
    val cardBgColor: Color,
    val contentColor: Color,
    val iconBgColor: Color = Color.White.copy(alpha = 0.4f)
)