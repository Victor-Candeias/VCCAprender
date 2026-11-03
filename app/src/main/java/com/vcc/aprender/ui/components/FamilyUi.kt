package com.vcc.aprender.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.vcc.aprender.ui.theme.AprenderColors

@Composable
fun FamilyHeader(icon: BrandSymbol, title: String, subtitle: String, background: Color = AprenderColors.Sky) {
    Column(
        modifier = Modifier.fillMaxWidth().background(background, RoundedCornerShape(24.dp)).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BrandArtwork(icon, description = null)
        Text(title, modifier = Modifier.semantics { heading() }, style = MaterialTheme.typography.headlineMedium, color = AprenderColors.Ink)
        Text(subtitle, style = MaterialTheme.typography.bodyLarge, color = AprenderColors.Ink)
    }
}

@Composable
fun FamilySection(title: String, icon: BrandSymbol, background: Color = AprenderColors.Mint, content: @Composable () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth().background(background).padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
                BrandArtwork(icon, description = null)
                Text(title, modifier = Modifier.semantics { heading() }, style = MaterialTheme.typography.titleLarge, color = AprenderColors.Ink)
            }
            content()
        }
    }
}

@Composable
fun FamilyProgress(value: Int, label: String) {
    val percent = value.coerceIn(0, 100)
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text("$label: $percent%", modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }, style = MaterialTheme.typography.bodyLarge)
        LinearProgressIndicator(
            progress = { percent / 100f },
            modifier = Modifier.fillMaxWidth().clearAndSetSemantics { },
            color = AprenderColors.Blue,
            trackColor = AprenderColors.Sky
        )
    }
}
