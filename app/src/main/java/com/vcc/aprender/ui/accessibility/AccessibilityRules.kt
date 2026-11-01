package com.vcc.aprender.ui.accessibility

/**
 * Pure, deterministic policies used by both Compose and local JVM tests.
 * Text and state labels must remain meaningful without colour or animation.
 */
object AnswerAccessibility {
    fun state(selected: Boolean, correct: Boolean): String? = when {
        selected && correct -> "Selecionada, resposta correta"
        selected -> "Selecionada, tenta novamente"
        correct -> "Resposta correta"
        else -> null
    }
}

/** WCAG relative-luminance and contrast computations for opaque sRGB UI colours. */
object ContrastRules {
    fun relativeLuminance(argb: Int): Double {
        fun channel(value: Int): Double {
            val normalized = value / 255.0
            return if (normalized <= 0.04045) normalized / 12.92
                else Math.pow((normalized + 0.055) / 1.055, 2.4)
        }
        val red = channel((argb ushr 16) and 0xFF)
        val green = channel((argb ushr 8) and 0xFF)
        val blue = channel(argb and 0xFF)
        return 0.2126 * red + 0.7152 * green + 0.0722 * blue
    }

    fun ratio(foreground: Int, background: Int): Double {
        val first = relativeLuminance(foreground)
        val second = relativeLuminance(background)
        return (maxOf(first, second) + 0.05) / (minOf(first, second) + 0.05)
    }
}
