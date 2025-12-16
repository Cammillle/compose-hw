package com.example.cupcake.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Pink600,
    secondary = Purple400,
    tertiary = Pink950
)

private val LightColorScheme = lightColorScheme(
    primary = Pink600,
    primaryContainer = Pink950,
    onPrimary = White,
    secondary = Purple400,
    secondaryContainer = Purple700,
    onSecondary = Black,
    background = White,
    surface = White,
    onBackground = Black,
    onSurface = Black,
    error = Color(0xFFB3261E),
    onError = White,
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color(0xFF616161),
    outline = Color(0xFFE0E0E0)

)

@Composable
fun CupcakeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}