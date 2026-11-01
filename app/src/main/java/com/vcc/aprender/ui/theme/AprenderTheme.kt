package com.vcc.aprender.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** Shared, high-contrast visual foundation for the children's learning experience. */
object AprenderColors {
    val Blue = Color(0xFF2156B5)
    val Navy = Color(0xFF17315C)
    val Sky = Color(0xFFE7F4FF)
    val Cream = Color(0xFFFFFBF2)
    val Coral = Color(0xFFFFE0D8)
    val Mint = Color(0xFFDDF7E9)
    val Lavender = Color(0xFFF0E6FF)
    val Sunshine = Color(0xFFFFF0BA)
    val Ink = Color(0xFF24324B)
}

private val ChildPalette = lightColorScheme(
    primary = AprenderColors.Blue,
    onPrimary = Color.White,
    primaryContainer = AprenderColors.Sky,
    onPrimaryContainer = AprenderColors.Navy,
    secondary = Color(0xFF775000),
    onSecondary = Color.White,
    secondaryContainer = AprenderColors.Sunshine,
    onSecondaryContainer = AprenderColors.Ink,
    tertiary = Color(0xFF286A4A),
    tertiaryContainer = AprenderColors.Mint,
    background = AprenderColors.Cream,
    onBackground = AprenderColors.Ink,
    surface = Color.White,
    onSurface = AprenderColors.Ink,
    surfaceVariant = AprenderColors.Sky,
    onSurfaceVariant = AprenderColors.Navy
)

private val BaseTypography = Typography()
private val ChildTypography = Typography(
    headlineLarge = BaseTypography.headlineLarge.copy(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold),
    headlineMedium = BaseTypography.headlineMedium.copy(fontSize = 27.sp, fontWeight = FontWeight.Bold),
    titleLarge = BaseTypography.titleLarge.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
    titleMedium = BaseTypography.titleMedium.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
    bodyLarge = BaseTypography.bodyLarge.copy(fontSize = 18.sp),
    labelLarge = BaseTypography.labelLarge.copy(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
)

@Composable
fun AprenderTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = ChildPalette, typography = ChildTypography, content = content)
}
