package com.emp.qscan.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = NoctuaBrown,
    onPrimary = BeigeLight,
    background = Beige,
    onBackground = BrownInk,
    surface = Beige,
    onSurface = BrownInk,
    surfaceVariant = BeigeLight,
    onSurfaceVariant = BrownMuted,
    outline = BrownInk,
    outlineVariant = BeigeLine,
    error = Brick,
    onError = BeigeLight,
)

private val DarkColors = darkColorScheme(
    primary = NoctuaTan,
    onPrimary = NightBrown,
    background = NightBrown,
    onBackground = Beige,
    surface = NightBrown,
    onSurface = Beige,
    surfaceVariant = NightSurface,
    onSurfaceVariant = NightMuted,
    outline = Beige,
    outlineVariant = NightLine,
    error = BrickLight,
    onError = NightBrown,
)

private val Sharp = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(6.dp),
    large = RoundedCornerShape(8.dp),
    extraLarge = RoundedCornerShape(8.dp),
)

// Fixed brand palette: dynamic color would replace the Noctua scheme on Android 12+.
@Composable
fun QScanTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        shapes = Sharp,
        content = content,
    )
}
