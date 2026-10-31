package com.vcc.aprender.audio

import java.util.Locale

object PortugueseSpeech {
    val preferredLocale: Locale = Locale("pt", "PT")
    fun normalized(text: String): String = text.trim().replace(Regex("\\s+"), " ")
    fun canSpeak(text: String): Boolean = normalized(text).isNotBlank()
}
