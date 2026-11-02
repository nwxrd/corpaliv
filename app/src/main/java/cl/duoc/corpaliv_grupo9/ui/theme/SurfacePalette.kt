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
    val badgeBackground: Color,
    val badgeText: Color,
    val actionBackground: Color,
    val actionForeground: Color,
    val actionIconColor: Color,
    val heroScrim: Color,
    val infoHeroStart: Color,
    val infoHeroEnd: Color,
    val text: Color,
    val secondaryText: Color,
    val outline: Color,
    val selectorTrack: Color,
    val cardBlue: Color,
    val cardViolet: Color,
    val cardVioletLight: Color,
    val educationSurface: Color,
    val employmentSurface: Color,
    val loginBackground: Color,
    val formSurface: Color
)

private val LightSurfacePalette = SurfacePalette(
    background = HomeBackground,
    surface = White,
    primary = BluePrimary,
    badgeBackground = White,
    badgeText = BluePrimary,
    actionBackground = BluePrimary,
    actionForeground = White,
    actionIconColor = BluePrimary,
    heroScrim = HomeHeroNight,
    infoHeroStart = Color(0xFF202B38),
    infoHeroEnd = Color(0xFF293644),
    text = HomeText,
    secondaryText = HomeTextSecondary,
    outline = HomeOutline,
    selectorTrack = HomeSwitchTrack,
    cardBlue = HomeCardBlue,
    cardViolet = HomeCardViolet,
    cardVioletLight = HomeCardVioletLight,
    educationSurface = InfoEducationSurface,
    employmentSurface = InfoEmploymentSurface,
    loginBackground = White,
    formSurface = HomeBackground
)

private val DarkSurfacePalette = SurfacePalette(
    background = Slate900,
    surface = Slate800,
    primary = BluePrimaryDark,
    badgeBackground = Color(0xFFE2E8F0),
    badgeText = Color(0xFF1E293B),
    actionBackground = BluePrimaryDark,
    actionForeground = Color(0xFF142033),
    actionIconColor = Color(0xFF142033),
    heroScrim = Color(0xFF222B36),
    infoHeroStart = Color(0xFF202B38),
    infoHeroEnd = Color(0xFF293644),
    text = Slate50,
    secondaryText = Slate400,
    outline = Slate700,
    selectorTrack = Slate800,
    cardBlue = Color(0xFF233B68),
    cardViolet = Color(0xFF392F60),
    cardVioletLight = Color(0xFF302A4A),
    educationSurface = Color(0xFF283253),
    employmentSurface = Color(0xFF1E3A45),
    loginBackground = Slate900,
    formSurface = Slate800
)

@Composable
fun corpalivSurfacePalette(): SurfacePalette {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f
    return if (isDark) DarkSurfacePalette else LightSurfacePalette
}
