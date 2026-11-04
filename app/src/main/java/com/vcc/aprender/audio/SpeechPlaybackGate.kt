package com.vcc.aprender.audio

import java.util.concurrent.atomic.AtomicReference

/**
 * Only one TTS utterance can be active. An old completion callback must not
 * unlock a newer utterance after a stop/restart.
 */
internal class SpeechPlaybackGate {
    private val activeId = AtomicReference<String?>(null)

    val isBusy: Boolean get() = activeId.get() != null

    fun begin(utteranceId: String): Boolean {
        require(utteranceId.isNotBlank())
        return activeId.compareAndSet(null, utteranceId)
    }

    fun finish(utteranceId: String?): Boolean =
        utteranceId != null && activeId.compareAndSet(utteranceId, null)

    fun cancel(): Boolean = activeId.getAndSet(null) != null
}
