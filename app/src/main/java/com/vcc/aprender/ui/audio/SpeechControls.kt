package com.vcc.aprender.ui.audio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vcc.aprender.audio.AndroidSpeechEngine
import com.vcc.aprender.audio.SpeechStatus
import com.vcc.aprender.data.settings.ParentalSettings
import com.vcc.aprender.data.settings.ParentalSettingsRepository

/**
 * The existing speech engine handles both question and optional answer feedback.
 * Never speaks automatically: audio is controlled by parents and explicitly requested by the child.
 */
@Composable
fun SpeechControls(textToRead: String, feedbackText: String? = null) {
    val context = LocalContext.current
    val speech = remember(context) { AndroidSpeechEngine(context) }
    val status by speech.status.collectAsStateWithLifecycle()
    val repository = remember(context) { ParentalSettingsRepository(context) }
    val settings by repository.settings.collectAsStateWithLifecycle(initialValue = ParentalSettings())
    LaunchedEffect(settings.audioEnabled, settings.speechRate) {
        speech.configure(settings.speechRate)
        if (!settings.audioEnabled) speech.stop()
    }
    DisposableEffect(speech) { onDispose { speech.release() } }
    if (!settings.audioEnabled) return
    when (status) {
        SpeechStatus.READY -> Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = { speech.speak(textToRead) },
                modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)
                    .semantics { contentDescription = "Ouvir pergunta e opções" }
            ) { Text("Ouvir pergunta") }
            if (!feedbackText.isNullOrBlank()) {
                OutlinedButton(
                    onClick = { speech.speak(feedbackText) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)
                        .semantics { contentDescription = "Ouvir resultado da resposta" }
                ) { Text("Ouvir resultado") }
            }
        }
        SpeechStatus.INITIALIZING -> Text("A preparar a voz portuguesa…",
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite })
        SpeechStatus.UNAVAILABLE -> Text(
            "Áudio indisponível. Verifica se a voz português (Portugal) está instalada."
        )
    }
}
