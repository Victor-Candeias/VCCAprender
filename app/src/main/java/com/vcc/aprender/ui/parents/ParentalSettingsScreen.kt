package com.vcc.aprender.ui.parents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.vcc.aprender.data.settings.ParentalSettings
import com.vcc.aprender.ui.components.FamilyHeader
import com.vcc.aprender.ui.components.FamilySection
import com.vcc.aprender.ui.theme.AprenderColors

@Composable
fun ParentalSettingsScreen(
    settings: ParentalSettings,
    onAudioChanged: (Boolean) -> Unit,
    onSpeechRateChanged: (Float) -> Unit,
    onAnimationsChanged: (Boolean) -> Unit = {},
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.Start
    ) {
        FamilyHeader("🛡️", "Área parental", "Personaliza a experiência de aprendizagem.", AprenderColors.Sky)
        FamilySection("Leitura por voz", "🔊", AprenderColors.Mint) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(if (settings.audioEnabled) "Áudio ativado" else "Áudio desativado",
                    style = MaterialTheme.typography.bodyLarge)
                Switch(checked = settings.audioEnabled, onCheckedChange = onAudioChanged)
            }
            Text("Velocidade da voz: ${(settings.speechRate * 100).toInt()}%")
            Slider(
                value = settings.speechRate,
                onValueChange = onSpeechRateChanged,
                valueRange = 0.5f..1.5f,
                steps = 9,
                enabled = settings.audioEnabled
            )
        }
        FamilySection("Movimento e animações", "⭐", AprenderColors.Lavender) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(if (settings.animationsEnabled) "Animações ativadas" else "Animações desativadas",
                    style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
                Switch(
                    checked = settings.animationsEnabled,
                    onCheckedChange = onAnimationsChanged,
                    modifier = Modifier.semantics { contentDescription = "Animações" }
                )
            }
            Text("Desativa os movimentos para uma experiência mais tranquila. As preferências de acessibilidade do Android também são respeitadas.")
        }
        FamilySection("Privacidade", "🔒", AprenderColors.Sunshine) {
            Text("As preferências ficam guardadas neste dispositivo. Não é necessária ligação à Internet.")
            Text("A gestão dos perfis e o progresso de aprendizagem estão disponíveis no menu inicial.")
        }
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Voltar ao início") }
    }
}
