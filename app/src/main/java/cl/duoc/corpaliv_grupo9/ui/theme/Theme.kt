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
    primaryContainer = BlueContainerDark,
    onPrimaryContainer = BlueContainer,
    secondary = BluePrimaryDark,
    onSecondary = BlueOnPrimaryDark,
    tertiary = BluePrimaryDark,
    onTertiary = BlueOnPrimaryDark,
    background = Slate900,
    onBackground = Slate50,
    surface = Slate800,
    surfaceVariant = Slate700,
    onSurface = Slate50,
    onSurfaceVariant = Slate400,
    outline = Slate700,
    outlineVariant = Slate700,
    error = Red400,
    onError = Slate950,
    errorContainer = Red950
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = White,
    primaryContainer = BlueContainer,
    onPrimaryContainer = BlueOnContainer,
    secondary = BluePrimary,
    onSecondary = White,
    tertiary = BluePrimary,
    onTertiary = White,
    background = White,
    onBackground = Slate900,
    surface = White,
    surfaceVariant = Slate50,
    onSurface = Slate900,
    onSurfaceVariant = Slate500,
    outline = Slate200,
    outlineVariant = Slate300,
    error = Red600,
    onError = White,
    errorContainer = Red100
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
