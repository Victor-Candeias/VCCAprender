package com.vcc.aprender

import com.vcc.aprender.domain.cards.CardOption
import com.vcc.aprender.domain.cards.DiscoverCard
import com.vcc.aprender.domain.cards.evaluate
import com.vcc.aprender.domain.model.LearningTopic
import org.junit.Assert.*
import org.junit.Test

class DomainValidationTest {
    private fun card(options: List<CardOption> = listOf(CardOption("a", "A"), CardOption("b", "B"))) =
        DiscoverCard("card", LearningTopic.LETTERS, "Escolhe", options, "a")

    @Test(expected = IllegalArgumentException::class)
    fun optionRejectsBlankId() { CardOption(" ", "Texto") }

    @Test(expected = IllegalArgumentException::class)
    fun optionRejectsBlankLabel() { CardOption("a", " ") }

    @Test(expected = IllegalArgumentException::class)
    fun cardRejectsDuplicateOptionIds() {
        card(listOf(CardOption("a", "A"), CardOption("a", "Outra")))
    }

    @Test(expected = IllegalArgumentException::class)
    fun cardRejectsSingleOption() { card(listOf(CardOption("a", "A"))) }

    @Test(expected = IllegalArgumentException::class)
    fun cardRejectsMoreThanFourOptions() {
        card((0..4).map { CardOption(if (it == 0) "a" else "id$it", "Opção $it") })
    }

    @Test(expected = IllegalArgumentException::class)
    fun cardRejectsBlankQuestion() {
        DiscoverCard("id", LearningTopic.LETTERS, " ", listOf(CardOption("a", "A"), CardOption("b", "B")), "a")
    }

    @Test(expected = IllegalArgumentException::class)
    fun evaluationRejectsUnknownOption() { card().evaluate("unknown") }

    @Test fun evaluationReturnsCardIdAndSelectedOption() {
        val result = card().evaluate("b")
        assertEquals("card", result.cardId)
        assertEquals("b", result.selectedOptionId)
        assertFalse(result.correct)
    }
}
