package com.vcc.aprender.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Only settings that support Finger Magic remain in the application menu.
 * The old reading, ABC-card game and card editor are no longer reachable.
 */
@Composable
fun SettingsScreen(
    onProfiles: () -> Unit,
    onProgress: () -> Unit,
    onParents: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Configurações", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Gere os perfis, acompanha os resultados do jogo e ajusta as preferências.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Start
        )
        SettingsButton("Mudar de perfil", onProfiles)
        SettingsButton("Ver progresso", onProgress)
        SettingsButton("Área parental e preferências", onParents)
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)) {
            Text("Voltar ao início")
        }
    }
}

@Composable
private fun SettingsButton(label: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp)) {
        Text(label)
    }
}
