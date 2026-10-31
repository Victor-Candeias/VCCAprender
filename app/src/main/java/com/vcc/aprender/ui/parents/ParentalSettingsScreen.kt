package com.vcc.aprender.ui.parents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vcc.aprender.data.settings.ParentalSettings

@Composable
fun ParentalSettingsScreen(
    settings: ParentalSettings,
    onAudioChanged: (Boolean) -> Unit,
    onSpeechRateChanged: (Float) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Área parental", style = MaterialTheme.typography.headlineMedium)
        Text("Preferências guardadas neste dispositivo. Não é necessária ligação à internet.")
        Text("Leitura por voz", style = MaterialTheme.typography.titleMedium)
        Switch(checked = settings.audioEnabled, onCheckedChange = onAudioChanged)
        Text(if (settings.audioEnabled) "Áudio ativado" else "Áudio desativado")
        Text("Velocidade da voz: ${(settings.speechRate * 100).toInt()}%")
        Slider(
            value = settings.speechRate,
            onValueChange = onSpeechRateChanged,
            valueRange = 0.5f..1.5f,
            steps = 9,
            enabled = settings.audioEnabled
        )
        Text("A gestão dos perfis e o progresso de aprendizagem estão disponíveis no menu inicial.")
        Button(onClick = onBack) { Text("Voltar ao início") }
    }
}
