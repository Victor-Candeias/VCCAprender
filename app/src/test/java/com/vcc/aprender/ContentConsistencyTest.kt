package com.vcc.aprender

import com.vcc.aprender.data.cards.OfflineCardCatalog
import com.vcc.aprender.domain.activities.ActivityContent
import com.vcc.aprender.domain.alphabet.AlphabetContent
import com.vcc.aprender.domain.model.LearningTopic
import org.junit.Assert.*
import org.junit.Test

class ContentConsistencyTest {
    @Test fun alphabetHasAllLettersInOrder() {
        val letters = AlphabetContent.cards.map { it.letter }
        assertEquals(('A'..'Z').toList(), letters)
    }

    @Test fun alphabetChoicesAreDistinctAndContainCorrectLetter() {
        AlphabetContent.cards.indices.forEach { index ->
            val choices = AlphabetContent.optionsFor(index)
            assertEquals(3, choices.size)
            assertEquals(3, choices.distinct().size)
            assertTrue(AlphabetContent.cards[index].letter in choices)
        }
    }

    @Test fun eachAvailableTopicHasValidContent() {
        LearningTopic.entries.forEach { topic ->
            val questions = ActivityContent.questions(topic)
            val cards = OfflineCardCatalog.byTopic(topic)
            assertTrue("Tema sem conteúdo: $topic", questions.isNotEmpty() || cards.isNotEmpty() || topic == LearningTopic.LETTERS)
            questions.forEach { question ->
                assertEquals(3, question.options.distinct().size)
                assertTrue(question.correctAnswer in question.options)
            }
        }
    }

    @Test fun allCardsHaveUniqueIdsAndValidOptions() {
        val cards = OfflineCardCatalog.cards
        assertEquals(cards.size, cards.map { it.id }.distinct().size)
        cards.forEach { card ->
            assertTrue(card.options.any { it.id == card.correctOptionId })
            assertEquals(card.options.size, card.options.map { it.id }.distinct().size)
        }
    }
}
