package cl.duoc.corpaliv_grupo9.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class AporteImpacto(
    val tag: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconBgColor: Color,
    val destination: String
)