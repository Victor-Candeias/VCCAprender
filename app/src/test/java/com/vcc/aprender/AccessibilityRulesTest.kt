package com.vcc.aprender

import com.vcc.aprender.ui.accessibility.AnswerAccessibility
import com.vcc.aprender.ui.accessibility.ContrastRules
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AccessibilityRulesTest {
    @Test fun responsesAlwaysHaveNonColourStateDescriptions() {
        assertNull(AnswerAccessibility.state(selected = false, correct = false))
        assertEquals("Selecionada, resposta correta",
            AnswerAccessibility.state(selected = true, correct = true))
        assertEquals("Selecionada, tenta novamente",
            AnswerAccessibility.state(selected = true, correct = false))
        assertEquals("Resposta correta",
            AnswerAccessibility.state(selected = false, correct = true))
    }

    @Test fun mainInkIsReadableOnEveryLearningCardBackground() {
        val ink = 0xFF24324B.toInt()
        listOf(
            0xFFFFFBF2.toInt(), // Cream
            0xFFE7F4FF.toInt(), // Sky
            0xFFFFE0D8.toInt(), // Coral
            0xFFDDF7E9.toInt(), // Mint
            0xFFF0E6FF.toInt(), // Lavender
            0xFFFFF0BA.toInt()  // Sunshine
        ).forEach { background ->
            assertTrue("O contraste de texto deve ser pelo menos 4.5:1",
                ContrastRules.ratio(ink, background) >= 4.5)
        }
    }

    @Test fun primaryActionAndSecondaryTextPassNormalTextContrast() {
        assertTrue(ContrastRules.ratio(0xFFFFFFFF.toInt(), 0xFF2156B5.toInt()) >= 4.5)
        assertTrue(ContrastRules.ratio(0xFF17315C.toInt(), 0xFFE7F4FF.toInt()) >= 4.5)
        assertTrue(ContrastRules.ratio(0xFF775000.toInt(), 0xFFFFF0BA.toInt()) >= 4.5)
    }

    @Test fun contrastRatioIsSymmetricAndBlackOnWhiteIsTwentyOne() {
        val black = 0xFF000000.toInt()
        val white = 0xFFFFFFFF.toInt()
        assertEquals(21.0, ContrastRules.ratio(black, white), 0.001)
        assertEquals(ContrastRules.ratio(black, white),
            ContrastRules.ratio(white, black), 0.001)
    }
}
