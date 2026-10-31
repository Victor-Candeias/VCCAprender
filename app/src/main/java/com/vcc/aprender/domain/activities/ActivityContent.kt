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

    private val animals = listOf(
        Triple("Gato", "🐱", listOf("Gato", "Cão", "Coelho")),
        Triple("Cão", "🐶", listOf("Cão", "Gato", "Vaca")),
        Triple("Vaca", "🐄", listOf("Vaca", "Cavalo", "Ovelha")),
        Triple("Leão", "🦁", listOf("Leão", "Tigre", "Urso")),
        Triple("Elefante", "🐘", listOf("Elefante", "Girafa", "Hipopótamo")),
        Triple("Borboleta", "🦋", listOf("Borboleta", "Abelha", "Joaninha")),
        Triple("Peixe", "🐟", listOf("Peixe", "Pássaro", "Tartaruga")),
        Triple("Coelho", "🐰", listOf("Coelho", "Gato", "Rato"))
    ).map { (name, symbol, choices) ->
        LearningQuestion("Que animal é este?", symbol, choices.sorted(), name)
    }

    private val sounds = listOf(
        Triple("Miau", "🐱", listOf("Gato", "Cão", "Vaca")),
        Triple("Au-au", "🐶", listOf("Cão", "Gato", "Pato")),
        Triple("Muuu", "🐄", listOf("Vaca", "Ovelha", "Cavalo")),
        Triple("Mééé", "🐑", listOf("Ovelha", "Vaca", "Gato")),
        Triple("Quá-quá", "🦆", listOf("Pato", "Cão", "Leão")),
        Triple("Cocorocó", "🐓", listOf("Galo", "Pato", "Cão"))
    ).map { (sound, symbol, choices) ->
        val answer = when (sound) {
            "Miau" -> "Gato"
            "Au-au" -> "Cão"
            "Muuu" -> "Vaca"
            "Mééé" -> "Ovelha"
            "Quá-quá" -> "Pato"
            else -> "Galo"
        }
        LearningQuestion("Que animal faz o som «$sound»?", symbol, choices.sorted(), answer)
    }

    private val words = listOf(
        Triple("Casa", "🏠", listOf("Casa", "Bola", "Livro")),
        Triple("Bola", "⚽", listOf("Bola", "Mesa", "Árvore")),
        Triple("Livro", "📚", listOf("Livro", "Carro", "Maçã")),
        Triple("Maçã", "🍎", listOf("Maçã", "Banana", "Pera")),
        Triple("Carro", "🚗", listOf("Carro", "Barco", "Avião")),
        Triple("Sol", "☀️", listOf("Sol", "Lua", "Estrela")),
        Triple("Flor", "🌸", listOf("Flor", "Folha", "Árvore")),
        Triple("Chave", "🔑", listOf("Chave", "Porta", "Janela"))
    ).map { (name, symbol, choices) ->
        LearningQuestion("Que palavra corresponde à imagem?", symbol, choices.sorted(), name)
    }

    fun questions(topic: LearningTopic): List<LearningQuestion> = when (topic) {
        LearningTopic.NUMBERS -> numbers
        LearningTopic.COLORS -> colors
        LearningTopic.SHAPES -> shapes
        LearningTopic.ANIMALS -> animals
        LearningTopic.SOUNDS -> sounds
        LearningTopic.WORDS -> words
        else -> emptyList()
    }
}
