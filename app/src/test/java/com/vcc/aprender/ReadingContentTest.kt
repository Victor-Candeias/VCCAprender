package com.vcc.aprender

import com.vcc.aprender.domain.activities.ActivityContent
import org.junit.Assert.*
import org.junit.Test

class ReadingContentTest {
    @Test fun readingHasTwentyValidQuestions() {
        val questions = ActivityContent.readingQuestions()
        assertEquals(20, questions.size)
        assertTrue(questions.all { it.prompt.isNotBlank() && it.illustration.isNotBlank() })
        assertTrue(questions.all { it.options.size == 3 && it.options.distinct().size == 3 })
        assertTrue(questions.all { it.correctAnswer in it.options })
    }
    @Test fun readingWordsAreUnique() {
        val words = ActivityContent.readingQuestions().map { it.correctAnswer }
        assertEquals(words.size, words.distinct().size)
    }
}
