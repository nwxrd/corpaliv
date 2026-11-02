package cl.duoc.corpaliv_grupo9.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

@Immutable
data class SurfacePalette(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val text: Color,
    val secondaryText: Color,
    val outline: Color,
    val selectorTrack: Color,
    val cardBlue: Color,
    val cardViolet: Color,
    val cardVioletLight: Color,
    val educationSurface: Color,
    val employmentSurface: Color,
    val formSurface: Color
)

private val LightSurfacePalette = SurfacePalette(
    background = HomeBackground,
    surface = White,
    primary = BluePrimary,
    text = HomeText,
    secondaryText = HomeTextSecondary,
    outline = HomeOutline,
    selectorTrack = HomeSwitchTrack,
    cardBlue = HomeCardBlue,
    cardViolet = HomeCardViolet,
    cardVioletLight = HomeCardVioletLight,
    educationSurface = InfoEducationSurface,
    employmentSurface = InfoEmploymentSurface,
    formSurface = AdminFormSurface
)

private val DarkSurfacePalette = SurfacePalette(
    background = Slate950,
    surface = Slate900,
    primary = BluePrimaryDark,
    text = Slate50,
    secondaryText = Slate400,
    outline = Slate700,
    selectorTrack = Slate800,
    cardBlue = Color(0xFF233B68),
    cardViolet = Color(0xFF392F60),
    cardVioletLight = Color(0xFF302A4A),
    educationSurface = Color(0xFF283253),
    employmentSurface = Color(0xFF1E3A45),
    formSurface = Slate900
)

@Composable
fun corpalivSurfacePalette(): SurfacePalette {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    return if (isDark) DarkSurfacePalette else LightSurfacePalette
}
