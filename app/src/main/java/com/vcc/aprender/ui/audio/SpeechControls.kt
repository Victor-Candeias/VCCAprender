package com.vcc.aprender.ui.audio

import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vcc.aprender.audio.AndroidSpeechEngine
import com.vcc.aprender.audio.SpeechStatus

@Composable
fun SpeechControls(textToRead: String) {
    val context = LocalContext.current
    val speech = remember(context) { AndroidSpeechEngine(context) }
    val status by speech.status.collectAsStateWithLifecycle()
    DisposableEffect(speech) { onDispose { speech.release() } }
    when (status) {
        SpeechStatus.READY -> OutlinedButton(onClick = { speech.speak(textToRead) }) {
            Text("🔊 Ouvir novamente")
        }
        SpeechStatus.INITIALIZING -> Text("A preparar a voz portuguesa…")
        SpeechStatus.UNAVAILABLE -> Text("Áudio indisponível. Verifica se a voz português (Portugal) está instalada.")
    }
}
