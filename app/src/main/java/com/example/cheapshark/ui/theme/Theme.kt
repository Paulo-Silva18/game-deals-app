package com.example.cheapshark.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = DarkNavy,
    onPrimary = White,
    secondary = DealGreen,
    background = White,
    surface = White,
    onBackground = DarkNavy,
    onSurface = DarkNavy,
    surfaceVariant = LightGrey
)

private val DarkColors = darkColorScheme(
    primary = DealGreen,
    onPrimary = DarkNavy,
    secondary = DealGreen,
    background = DarkNavy,
    surface = DarkNavy,
    onBackground = White,
    onSurface = White
)

@Composable
fun CheapSharkAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
