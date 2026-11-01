package com.vcc.aprender

import com.vcc.aprender.data.cards.CardDraft
import com.vcc.aprender.data.cards.CustomCardEntity
import com.vcc.aprender.data.cards.toDiscoverCard
import com.vcc.aprender.domain.cards.CardDifficulty
import com.vcc.aprender.domain.model.LearningTopic
import org.junit.Assert.*
import org.junit.Test

class CustomCardValidationTest {
    private fun draft(options: List<String> = listOf("Gato", "Cão", "Vaca")) =
        CardDraft(LearningTopic.ANIMALS, CardDifficulty.MEDIUM, "Que animal é este?", options, 0)

    @Test fun validDraftNormalizesWhitespace() {
        val valid = draft(listOf(" Gato ", "Cão", "Vaca")).validated()
        assertEquals("Gato", valid.options.first())
    }

    @Test(expected = IllegalArgumentException::class)
    fun duplicateOptionsAreRejected() { draft(listOf("Gato", " gato ", "Vaca")).validated() }

    @Test(expected = IllegalArgumentException::class)
    fun blankQuestionIsRejected() { draft().copy(question = " ").validated() }

    @Test(expected = IllegalArgumentException::class)
    fun invalidCorrectIndexIsRejected() { draft().copy(correctIndex = 3).validated() }

    @Test fun storedEntityConvertsToGameCard() {
        val entity = CustomCardEntity(
            id = 12, topic = "ANIMALS", difficulty = "MEDIUM",
            question = "Que animal?", firstOption = "Gato", secondOption = "Cão",
            thirdOption = "Vaca", correctIndex = 1
        )
        val card = entity.toDiscoverCard()!!
        assertEquals("custom-12", card.id)
        assertEquals(CardDifficulty.MEDIUM, card.difficulty)
        assertEquals("option-1", card.correctOptionId)
    }

    @Test fun invalidStoredTopicIsSkipped() {
        assertNull(CustomCardEntity(
            id = 1, topic = "UNKNOWN", difficulty = "EASY",
            question = "Teste", firstOption = "A", secondOption = "B",
            thirdOption = "C", correctIndex = 0
        ).toDiscoverCard())
    }
}
