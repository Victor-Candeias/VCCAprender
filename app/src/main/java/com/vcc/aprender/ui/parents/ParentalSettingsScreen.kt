package com.vcc.aprender.ui.parents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import com.vcc.aprender.ui.components.AdaptiveTwoPaneScreen
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
    AdaptiveTwoPaneScreen(
        minimumWidthDp = 760f,
        primary = {
            FamilyHeader("🛡️", "Área parental",
                "Personaliza a experiência de aprendizagem.", AprenderColors.Sky)
            FamilySection("Leitura por voz", "🔊", AprenderColors.Mint) {
                Row(
                    modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        if (settings.audioEnabled) "Áudio ativado" else "Áudio desativado",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = settings.audioEnabled,
                        onCheckedChange = onAudioChanged,
                        modifier = Modifier.semantics { contentDescription = "Leitura por voz" }
                    )
                }
                Text("Velocidade da voz: ${(settings.speechRate * 100).toInt()}%")
                Slider(
                    value = settings.speechRate,
                    onValueChange = onSpeechRateChanged,
                    valueRange = 0.5f..1.5f,
                    steps = 9,
                    enabled = settings.audioEnabled,
                    modifier = Modifier.semantics { contentDescription = "Velocidade da voz" }
                )
            }
        },
        secondary = {
            FamilySection("Movimento e animações", "⭐", AprenderColors.Lavender) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        if (settings.animationsEnabled) "Animações ativadas" else "Animações desativadas",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
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
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)) {
                Text("Voltar ao início")
            }
        }
    )
}
