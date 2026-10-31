package com.vcc.aprender

import com.vcc.aprender.domain.alphabet.AlphabetContent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AlphabetContentTest {
    @Test fun alphabetHasAllLetters() {
        assertEquals(('A'..'Z').toList(), AlphabetContent.cards.map { it.letter })
    }

    @Test fun everyQuestionContainsCorrectAnswerAndThreeDistinctOptions() {
        AlphabetContent.cards.indices.forEach { index ->
            val options = AlphabetContent.optionsFor(index)
            assertEquals(3, options.distinct().size)
            assertTrue(options.contains(AlphabetContent.cards[index].letter))
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidIndexIsRejected() {
        AlphabetContent.optionsFor(-1)
    }
}
