package com.vcc.aprender.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Keep content comfortably readable on phones and tablets.
 * Width is constrained, but height remains scrollable in the owning screen.
 */
@Composable
fun ResponsiveContent(content: @Composable () -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        val contentWidth = if (maxWidth >= 600.dp) 640.dp else maxWidth
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.widthIn(max = contentWidth).fillMaxWidth()
        ) {
            content()
        }
    }
}
