package com.vcc.aprender

import com.vcc.aprender.domain.activities.ActivityContent
import com.vcc.aprender.domain.model.LearningTopic
import org.junit.Assert.*
import org.junit.Test

class AnimalsSoundsWordsTest {
    @Test fun allNewTopicsHaveValidOfflineQuestions() {
        listOf(LearningTopic.ANIMALS, LearningTopic.SOUNDS, LearningTopic.WORDS).forEach { topic ->
            val questions = ActivityContent.questions(topic)
            assertTrue("Sem perguntas para $topic", questions.isNotEmpty())
            questions.forEach { question ->
                assertEquals(3, question.options.size)
                assertEquals(3, question.options.distinct().size)
                assertTrue(question.correctAnswer in question.options)
                assertTrue(question.prompt.isNotBlank())
            }
        }
    }

    @Test fun soundQuestionsDoNotRevealAnswerAsAnimalArtwork() {
        val questions = ActivityContent.questions(LearningTopic.SOUNDS)
        assertTrue(questions.all { it.illustration == "sound" })
    }

    @Test fun wordsAndAnimalsHaveMultipleExamples() {
        assertTrue(ActivityContent.questions(LearningTopic.ANIMALS).size >= 6)
        assertTrue(ActivityContent.questions(LearningTopic.WORDS).size >= 6)
    }
}
