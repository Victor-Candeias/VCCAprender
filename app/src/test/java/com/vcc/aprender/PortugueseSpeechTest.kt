package com.vcc.aprender

import com.vcc.aprender.audio.PortugueseSpeech
import org.junit.Assert.*
import org.junit.Test

class PortugueseSpeechTest {
    @Test fun localeIsPortugueseFromPortugal() {
        assertEquals("pt", PortugueseSpeech.preferredLocale.language)
        assertEquals("PT", PortugueseSpeech.preferredLocale.country)
    }

    @Test fun whitespaceIsNormalized() {
        assertEquals("Olá mundo", PortugueseSpeech.normalized("  Olá   mundo  "))
    }

    @Test fun emptySpeechIsRejected() {
        assertFalse(PortugueseSpeech.canSpeak("  "))
        assertTrue(PortugueseSpeech.canSpeak("Abelha"))
    }
}
