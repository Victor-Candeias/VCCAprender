package com.vcc.aprender.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val ChildPalette = lightColorScheme(
    primary = Color(0xFF3658A4),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCE8FF),
    onPrimaryContainer = Color(0xFF152D60),
    secondary = Color(0xFF806000),
    secondaryContainer = Color(0xFFFFE7A3),
    background = Color(0xFFFFFCF4),
    onBackground = Color(0xFF252525),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF252525)
)

private val ChildTypography = Typography(
    headlineLarge = Typography().headlineLarge.copy(fontSize = 32.sp, fontWeight = FontWeight.Bold),
    titleLarge = Typography().titleLarge.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
    titleMedium = Typography().titleMedium.copy(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
    bodyLarge = Typography().bodyLarge.copy(fontSize = 18.sp)
)

@Composable
fun AprenderTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = ChildPalette, typography = ChildTypography, content = content)
}
