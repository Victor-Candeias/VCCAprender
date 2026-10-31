package com.vcc.aprender.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class SpeechStatus { INITIALIZING, READY, UNAVAILABLE }

class AndroidSpeechEngine(context: Context) : TextToSpeech.OnInitListener {
    private val _status = MutableStateFlow(SpeechStatus.INITIALIZING)
    val status = _status.asStateFlow()
    private var released = false
    private var engine: TextToSpeech? = TextToSpeech(context.applicationContext, this)

    override fun onInit(code: Int) {
        if (released) return
        val tts = engine
        if (code != TextToSpeech.SUCCESS || tts == null) {
            _status.value = SpeechStatus.UNAVAILABLE
            return
        }
        val available = tts.isLanguageAvailable(PortugueseSpeech.preferredLocale)
        if (available < TextToSpeech.LANG_COUNTRY_AVAILABLE) {
            _status.value = SpeechStatus.UNAVAILABLE
            return
        }
        val result = tts.setLanguage(PortugueseSpeech.preferredLocale)
        _status.value = if (result >= TextToSpeech.LANG_COUNTRY_AVAILABLE) {
            tts.setSpeechRate(0.85f)
            SpeechStatus.READY
        } else SpeechStatus.UNAVAILABLE
    }

    fun speak(text: String): Boolean {
        if (released || _status.value != SpeechStatus.READY || !PortugueseSpeech.canSpeak(text)) return false
        val result = engine?.speak(
            PortugueseSpeech.normalized(text),
            TextToSpeech.QUEUE_FLUSH,
            null,
            "vcc-aprender"
        )
        return result == TextToSpeech.SUCCESS
    }

    fun stop() { engine?.stop() }

    fun release() {
        released = true
        engine?.stop()
        engine?.shutdown()
        engine = null
        _status.value = SpeechStatus.UNAVAILABLE
    }
}
