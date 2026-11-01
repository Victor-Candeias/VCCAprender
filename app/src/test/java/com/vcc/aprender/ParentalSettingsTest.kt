package com.vcc.aprender

import com.vcc.aprender.data.settings.ParentalSettings
import org.junit.Assert.*
import org.junit.Test

class ParentalSettingsTest {
    @Test fun defaultSettingsEnableAudioAtChildFriendlyRate() {
        val settings = ParentalSettings()
        assertTrue(settings.audioEnabled)
        assertEquals(0.85f, settings.speechRate, 0.001f)
        assertTrue(settings.animationsEnabled)
    }

    @Test fun audioCanBeDisabled() {
        assertFalse(ParentalSettings(audioEnabled = false).audioEnabled)
    }

    @Test fun animationsCanBeDisabledWithoutAffectingAudio() {
        val settings = ParentalSettings(animationsEnabled = false)
        assertFalse(settings.animationsEnabled)
        assertTrue(settings.audioEnabled)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rateBelowMinimumIsRejected() {
        ParentalSettings(speechRate = 0.1f)
    }

    @Test(expected = IllegalArgumentException::class)
    fun rateAboveMaximumIsRejected() {
        ParentalSettings(speechRate = 2.0f)
    }
}
