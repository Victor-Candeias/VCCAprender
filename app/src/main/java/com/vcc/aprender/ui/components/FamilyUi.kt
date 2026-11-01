package com.vcc.aprender.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.vcc.aprender.ui.theme.AprenderColors

@Composable
fun FamilyHeader(icon: String, title: String, subtitle: String, background: Color = AprenderColors.Sky) {
    Column(
        modifier = Modifier.fillMaxWidth().background(background, RoundedCornerShape(24.dp)).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(icon, style = MaterialTheme.typography.displayMedium)
        Text(title, style = MaterialTheme.typography.headlineMedium, color = AprenderColors.Ink)
        Text(subtitle, style = MaterialTheme.typography.bodyLarge, color = AprenderColors.Ink)
    }
}

@Composable
fun FamilySection(title: String, icon: String, background: Color = AprenderColors.Mint, content: @Composable () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth().background(background).padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("$icon  $title", style = MaterialTheme.typography.titleLarge, color = AprenderColors.Ink)
            content()
        }
    }
}

@Composable
fun FamilyProgress(value: Int, label: String) {
    val percent = value.coerceIn(0, 100)
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text("$label: $percent%", style = MaterialTheme.typography.bodyLarge)
        LinearProgressIndicator(
            progress = { percent / 100f },
            modifier = Modifier.fillMaxWidth().semantics { contentDescription = "$label: $percent por cento" },
            color = AprenderColors.Blue,
            trackColor = AprenderColors.Sky
        )
    }
}
