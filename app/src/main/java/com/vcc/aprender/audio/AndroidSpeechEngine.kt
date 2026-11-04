package com.vcc.aprender.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class SpeechStatus { INITIALIZING, READY, UNAVAILABLE }

class AndroidSpeechEngine(context: Context) : TextToSpeech.OnInitListener {
    private val _status = MutableStateFlow(SpeechStatus.INITIALIZING)
    val status = _status.asStateFlow()

    private val playbackGate = SpeechPlaybackGate()
    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking = _isSpeaking.asStateFlow()

    private var speechRate = 0.85f
    @Volatile private var released = false
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
            tts.setSpeechRate(speechRate)
            tts.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) = Unit
                override fun onDone(utteranceId: String?) = finishSpeaking(utteranceId)
                override fun onError(utteranceId: String?) = finishSpeaking(utteranceId)
                override fun onStop(utteranceId: String?, interrupted: Boolean) =
                    finishSpeaking(utteranceId)
            })
            SpeechStatus.READY
        } else SpeechStatus.UNAVAILABLE
    }

    fun configure(rate: Float) {
        speechRate = rate.coerceIn(0.5f, 1.5f)
        if (!released && _status.value == SpeechStatus.READY) engine?.setSpeechRate(speechRate)
    }

    /** Reject another read request until Android reports completion, error, or stop. */
    fun speak(text: String): Boolean {
        if (released || _status.value != SpeechStatus.READY ||
            !PortugueseSpeech.canSpeak(text)) return false
        val tts = engine ?: return false
        val utteranceId = UUID.randomUUID().toString()
        if (!playbackGate.begin(utteranceId)) return false

        _isSpeaking.value = true
        val result = try {
            tts.speak(
                PortugueseSpeech.normalized(text),
                TextToSpeech.QUEUE_FLUSH,
                null,
                utteranceId
            )
        } catch (_: RuntimeException) {
            TextToSpeech.ERROR
        }
        if (result != TextToSpeech.SUCCESS) finishSpeaking(utteranceId)
        return result == TextToSpeech.SUCCESS
    }

    private fun finishSpeaking(utteranceId: String?) {
        // A late callback from an earlier utterance must not unblock the next one.
        if (playbackGate.finish(utteranceId)) _isSpeaking.value = false
    }

    fun stop() {
        playbackGate.cancel()
        _isSpeaking.value = false
        engine?.stop()
    }

    fun release() {
        released = true
        stop()
        engine?.shutdown()
        engine = null
        _status.value = SpeechStatus.UNAVAILABLE
    }
}
