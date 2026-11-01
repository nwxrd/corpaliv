package cl.duoc.corpaliv_grupo9.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BluePrimaryDark,
    onPrimary = BlueOnPrimaryDark,
    primaryContainer = HomeCardBlueDark,
    onPrimaryContainer = Slate50,
    secondary = InfoEducationSurfaceDark,
    onSecondary = Slate50,
    secondaryContainer = HomeCardVioletDark,
    onSecondaryContainer = Slate50,
    tertiary = InfoEmploymentSurfaceDark,
    onTertiary = Slate50,
    tertiaryContainer = HomeCardVioletLightDark,
    onTertiaryContainer = Slate50,
    background = Slate900,
    onBackground = Slate50,
    surface = Slate800,
    surfaceVariant = Slate800,
    onSurface = Slate50,
    onSurfaceVariant = Slate400,
    outline = Slate700,
    outlineVariant = Slate700,
    error = Red400,
    onError = Slate950,
    errorContainer = Red950,
    inverseSurface = InverseBadgeBackgroundDark,
    inverseOnSurface = InverseBadgeTextDark,
    scrim = HomeHeroNightDark
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = White,
    primaryContainer = HomeCardBlue,
    onPrimaryContainer = BlueOnContainer,
    secondary = InfoEducationSurface,
    onSecondary = Slate900,
    secondaryContainer = HomeCardViolet,
    onSecondaryContainer = Slate900,
    tertiary = InfoEmploymentSurface,
    onTertiary = Slate900,
    tertiaryContainer = HomeCardVioletLight,
    onTertiaryContainer = Slate900,
    background = HomeBackground,
    onBackground = HomeText,
    surface = White,
    surfaceVariant = HomeSwitchTrack,
    onSurface = HomeText,
    onSurfaceVariant = HomeTextSecondary,
    outline = HomeOutline,
    outlineVariant = Slate300,
    error = Red600,
    onError = White,
    errorContainer = Red100,
    inverseSurface = White,
    inverseOnSurface = BluePrimary,
    scrim = HomeHeroNight
)

@Composable
fun Corpaliv_Grupo9Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
