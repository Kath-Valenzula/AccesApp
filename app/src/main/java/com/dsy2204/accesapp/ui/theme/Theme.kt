package com.dsy2204.accesapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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

private val KidsColors = lightColorScheme(
    primary = Color(0xFF4FC3F7),
    onPrimary = Color(0xFF002027),
    secondary = Color(0xFFFFD54F),
    onSecondary = Color(0xFF331B00),
    tertiary = Color(0xFFFF8A80),
    onTertiary = Color(0xFF3E0A09),
    background = Color(0xFFFFFBF0),
    onBackground = Color(0xFF1B1B1B),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B1B1B),
    primaryContainer = Color(0xFFB3E5FC),
    onPrimaryContainer = Color(0xFF001F2A),
    secondaryContainer = Color(0xFFFFE082),
    onSecondaryContainer = Color(0xFF2A1A00),
    tertiaryContainer = Color(0xFFFFAB91),
    onTertiaryContainer = Color(0xFF2E0B08)
)

private val DefaultTypography = Typography()

private val KidsTypography = Typography(
    displayLarge = DefaultTypography.displayLarge.copy(fontSize = 44.sp, lineHeight = 48.sp),
    titleLarge = DefaultTypography.titleLarge.copy(fontSize = 26.sp, lineHeight = 30.sp, fontWeight = FontWeight.SemiBold),
    titleMedium = DefaultTypography.titleMedium.copy(fontSize = 22.sp, lineHeight = 26.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = TextStyle(fontSize = 18.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontSize = 16.sp, lineHeight = 22.sp),
    labelLarge = DefaultTypography.labelLarge.copy(fontSize = 16.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium)
)

@Composable
fun AccesAppTheme(
    highContrast: Boolean,
    kidsMode: Boolean,
    content: @Composable () -> Unit
) {
    val colors = when {
        kidsMode -> KidsColors
        highContrast -> HighContrastColors
        else -> DefaultLightColors
    }
    val typography = if (kidsMode) KidsTypography else DefaultTypography
    MaterialTheme(colorScheme = colors, typography = typography, content = content)
}
