package com.vcc.aprender

import com.vcc.aprender.audio.SpeechPlaybackGate
import org.junit.Assert.*
import org.junit.Test

class SpeechPlaybackGateTest {
    @Test fun secondRequestIsBlockedUntilCurrentUtteranceFinishes() {
        val gate = SpeechPlaybackGate()
        assertTrue(gate.begin("question-1"))
        assertTrue(gate.isBusy)
        assertFalse(gate.begin("question-2"))
        assertFalse(gate.begin("answer"))
        assertTrue(gate.finish("question-1"))
        assertFalse(gate.isBusy)
        assertTrue(gate.begin("answer"))
        assertTrue(gate.finish("answer"))
    }

    @Test fun oldCallbackCannotReleaseTheNewUtterance() {
        val gate = SpeechPlaybackGate()
        assertTrue(gate.begin("question-1"))
        assertTrue(gate.cancel())
        assertTrue(gate.begin("question-2"))
        assertFalse(gate.finish("question-1"))
        assertFalse(gate.finish(null))
        assertTrue(gate.isBusy)
        assertTrue(gate.finish("question-2"))
        assertFalse(gate.isBusy)
    }

    @Test fun errorOrCancellationUnlocksPlayback() {
        val gate = SpeechPlaybackGate()
        assertTrue(gate.begin("utterance"))
        assertTrue(gate.finish("utterance")) // onError uses the same completion path
        assertTrue(gate.begin("another"))
        assertTrue(gate.cancel())
        assertTrue(gate.begin("retry"))
    }
}
