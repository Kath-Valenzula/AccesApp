package com.dsy2204.accesapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val HighContrastColors = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF000000),
    background = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF000000),
    onSurface = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF000000),
    onPrimaryContainer = Color(0xFFFFFFFF)
)

private val DefaultLightColors = lightColorScheme()

@Composable
fun AccesAppTheme(highContrast: Boolean, content: @Composable () -> Unit) {
    val colors = if (highContrast) HighContrastColors else DefaultLightColors
    MaterialTheme(colorScheme = colors, content = content)
}
