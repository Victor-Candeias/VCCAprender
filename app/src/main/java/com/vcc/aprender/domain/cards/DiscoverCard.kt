package com.vcc.aprender.domain.cards

import com.vcc.aprender.domain.model.LearningTopic

enum class CardDifficulty { EASY, MEDIUM, HARD }

data class CardOption(
    val id: String,
    val label: String,
    val illustration: String? = null
) {
    init {
        require(id.isNotBlank()) { "O identificador da opção é obrigatório." }
        require(label.isNotBlank()) { "A opção deve ter texto." }
    }
}

data class DiscoverCard(
    val id: String,
    val topic: LearningTopic,
    val question: String,
    val options: List<CardOption>,
    val correctOptionId: String,
    val difficulty: CardDifficulty = CardDifficulty.EASY,
    val illustration: String? = null,
    val explanation: String = ""
) {
    init {
        require(id.isNotBlank()) { "O cartão precisa de identificador." }
        require(question.isNotBlank()) { "A pergunta é obrigatória." }
        require(options.size in 2..4) { "O cartão deve ter entre 2 e 4 opções." }
        require(options.map { it.id }.distinct().size == options.size) { "Opções duplicadas." }
        require(options.any { it.id == correctOptionId }) { "A resposta correta não existe." }
    }

    fun isCorrect(optionId: String): Boolean = optionId == correctOptionId
}

data class CardAnswer(
    val cardId: String,
    val selectedOptionId: String,
    val correct: Boolean
)

fun DiscoverCard.evaluate(optionId: String): CardAnswer {
    require(options.any { it.id == optionId }) { "Opção inválida." }
    return CardAnswer(id, optionId, isCorrect(optionId))
}
