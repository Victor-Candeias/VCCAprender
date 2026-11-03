package com.vcc.aprender.domain.cards

enum class FeedbackKind { CORRECT, INCORRECT }

data class AnswerFeedback(
    val kind: FeedbackKind,
    val title: String,
    val message: String
)

object AnswerFeedbackFactory {
    fun from(card: DiscoverCard, answer: CardAnswer): AnswerFeedback {
        require(card.id == answer.cardId) { "A resposta não pertence ao cartão." }
        require(card.options.any { it.id == answer.selectedOptionId }) { "Opção inválida." }
        require(card.isCorrect(answer.selectedOptionId) == answer.correct) { "Resultado inconsistente." }
        return if (answer.correct) {
            AnswerFeedback(FeedbackKind.CORRECT, "Muito bem!", "Acertaste! Continua assim!")
        } else {
            val correctLabel = card.options.first { it.id == card.correctOptionId }.label
            val explanation = card.explanation.trim()
            AnswerFeedback(
                FeedbackKind.INCORRECT, "Boa tentativa!",
                "A resposta certa é $correctLabel." + if (explanation.isNotEmpty()) " $explanation" else " Vamos ao próximo cartão!"
            )
        }
    }
}
