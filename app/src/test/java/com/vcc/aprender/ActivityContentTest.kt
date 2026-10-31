package com.vcc.aprender

import com.vcc.aprender.domain.activities.ActivityContent
import com.vcc.aprender.domain.model.LearningTopic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ActivityContentTest {
    @Test fun numbersCoverOneToTen() {
        assertEquals(10, ActivityContent.questions(LearningTopic.NUMBERS).size)
    }

    @Test fun everyQuestionHasThreeDistinctAnswersAndIncludesCorrectOne() {
        listOf(LearningTopic.NUMBERS, LearningTopic.COLORS, LearningTopic.SHAPES).forEach { topic ->
            assertTrue(ActivityContent.questions(topic).isNotEmpty())
            ActivityContent.questions(topic).forEach { question ->
                assertEquals(3, question.options.distinct().size)
                assertTrue(question.correctAnswer in question.options)
            }
        }
    }

    @Test fun unsupportedTopicsHaveNoQuestions() {
        assertTrue(ActivityContent.questions(LearningTopic.ANIMALS).isEmpty())
    }
}
