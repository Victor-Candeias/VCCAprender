package com.vcc.aprender.domain.activities

import com.vcc.aprender.domain.model.LearningTopic

data class LearningQuestion(
    val prompt: String,
    val illustration: String,
    val options: List<String>,
    val correctAnswer: String
) {
    init {
        require(options.size == 3 && options.distinct().size == 3)
        require(correctAnswer in options)
    }
}

object ActivityContent {
    private val numbers = (1..10).map { number ->
        val options = listOf(number, if (number == 10) 1 else number + 1, if (number == 1) 10 else number - 1)
            .map(Int::toString).sorted()
        LearningQuestion("Quantas estrelas vês?", "⭐".repeat(number), options, number.toString())
    }
    private val colors = listOf(
        Triple("Vermelho", "🔴", listOf("Vermelho", "Azul", "Verde")),
        Triple("Azul", "🔵", listOf("Azul", "Amarelo", "Vermelho")),
        Triple("Verde", "🟢", listOf("Verde", "Azul", "Laranja")),
        Triple("Amarelo", "🟡", listOf("Amarelo", "Verde", "Roxo")),
        Triple("Laranja", "🟠", listOf("Laranja", "Vermelho", "Azul")),
        Triple("Roxo", "🟣", listOf("Roxo", "Amarelo", "Verde"))
    ).map { (name, symbol, choices) ->
        LearningQuestion("Que cor é esta?", symbol, choices.sorted(), name)
    }
    private val shapes = listOf(
        Triple("Círculo", "●", listOf("Círculo", "Quadrado", "Triângulo")),
        Triple("Quadrado", "■", listOf("Quadrado", "Círculo", "Triângulo")),
        Triple("Triângulo", "▲", listOf("Triângulo", "Retângulo", "Círculo")),
        Triple("Retângulo", "▰", listOf("Retângulo", "Quadrado", "Círculo")),
        Triple("Estrela", "★", listOf("Estrela", "Círculo", "Triângulo")),
        Triple("Coração", "♥", listOf("Coração", "Estrela", "Quadrado"))
    ).map { (name, symbol, choices) ->
        LearningQuestion("Que forma é esta?", symbol, choices.sorted(), name)
    }

    fun questions(topic: LearningTopic): List<LearningQuestion> = when (topic) {
        LearningTopic.NUMBERS -> numbers
        LearningTopic.COLORS -> colors
        LearningTopic.SHAPES -> shapes
        else -> emptyList()
    }
}
