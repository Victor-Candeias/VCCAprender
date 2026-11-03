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
        LearningQuestion("Quantas estrelas vês?", "stars:$number", options, number.toString())
    }
    private val colors = listOf(
        Triple("Vermelho", "red", listOf("Vermelho", "Azul", "Verde")),
        Triple("Azul", "blue", listOf("Azul", "Amarelo", "Vermelho")),
        Triple("Verde", "green", listOf("Verde", "Azul", "Laranja")),
        Triple("Amarelo", "yellow", listOf("Amarelo", "Verde", "Roxo")),
        Triple("Laranja", "orange", listOf("Laranja", "Vermelho", "Azul")),
        Triple("Roxo", "purple", listOf("Roxo", "Amarelo", "Verde"))
    ).map { (name, symbol, choices) ->
        LearningQuestion("Que cor é esta?", symbol, choices.sorted(), name)
    }
    private val shapes = listOf(
        Triple("Círculo", "circle", listOf("Círculo", "Quadrado", "Triângulo")),
        Triple("Quadrado", "square", listOf("Quadrado", "Círculo", "Triângulo")),
        Triple("Triângulo", "triangle", listOf("Triângulo", "Retângulo", "Círculo")),
        Triple("Retângulo", "rectangle", listOf("Retângulo", "Quadrado", "Círculo")),
        Triple("Estrela", "star", listOf("Estrela", "Círculo", "Triângulo")),
        Triple("Coração", "heart", listOf("Coração", "Estrela", "Quadrado"))
    ).map { (name, symbol, choices) ->
        LearningQuestion("Que forma é esta?", symbol, choices.sorted(), name)
    }

    private val animals = listOf(
        Triple("Gato", "cat", listOf("Gato", "Cão", "Coelho")),
        Triple("Cão", "dog", listOf("Cão", "Gato", "Vaca")),
        Triple("Vaca", "cow", listOf("Vaca", "Cavalo", "Ovelha")),
        Triple("Leão", "lion", listOf("Leão", "Tigre", "Urso")),
        Triple("Elefante", "elephant", listOf("Elefante", "Girafa", "Hipopótamo")),
        Triple("Borboleta", "butterfly", listOf("Borboleta", "Abelha", "Joaninha")),
        Triple("Peixe", "fish", listOf("Peixe", "Pássaro", "Tartaruga")),
        Triple("Coelho", "rabbit", listOf("Coelho", "Gato", "Rato"))
    ).map { (name, symbol, choices) ->
        LearningQuestion("Que animal é este?", symbol, choices.sorted(), name)
    }

    private val sounds = listOf(
        Triple("Miau", "cat", listOf("Gato", "Cão", "Vaca")),
        Triple("Au-au", "dog", listOf("Cão", "Gato", "Pato")),
        Triple("Muuu", "cow", listOf("Vaca", "Ovelha", "Cavalo")),
        Triple("Mééé", "sheep", listOf("Ovelha", "Vaca", "Gato")),
        Triple("Quá-quá", "duck", listOf("Pato", "Cão", "Leão")),
        Triple("Cocorocó", "rooster", listOf("Galo", "Pato", "Cão"))
    ).map { (sound, _, choices) ->
        val answer = when (sound) {
            "Miau" -> "Gato"
            "Au-au" -> "Cão"
            "Muuu" -> "Vaca"
            "Mééé" -> "Ovelha"
            "Quá-quá" -> "Pato"
            else -> "Galo"
        }
        LearningQuestion("Que animal faz o som «$sound»?", "sound", choices.sorted(), answer)
    }

    private val words = listOf(
        Triple("Casa", "house", listOf("Casa", "Bola", "Livro")),
        Triple("Bola", "ball", listOf("Bola", "Mesa", "Árvore")),
        Triple("Livro", "book", listOf("Livro", "Carro", "Maçã")),
        Triple("Maçã", "apple", listOf("Maçã", "Banana", "Pera")),
        Triple("Carro", "car", listOf("Carro", "Barco", "Avião")),
        Triple("Sol", "sun", listOf("Sol", "Lua", "Estrela")),
        Triple("Flor", "flower", listOf("Flor", "Folha", "Árvore")),
        Triple("Chave", "key", listOf("Chave", "Porta", "Janela"))
    ).map { (name, symbol, choices) ->
        LearningQuestion("Que palavra corresponde à imagem?", symbol, choices.sorted(), name)
    }

    private val reading = listOf(
        Triple("Pato", "duck", listOf("Pato", "Gato", "Rato")),
        Triple("Rato", "mouse", listOf("Rato", "Pato", "Gato")),
        Triple("Dado", "die", listOf("Dado", "Dedo", "Dia")),
        Triple("Dedo", "finger", listOf("Dedo", "Dado", "Dia")),
        Triple("Pão", "bread", listOf("Pão", "Mão", "Cão")),
        Triple("Mão", "hand", listOf("Mão", "Pão", "Cão")),
        Triple("Cão", "dog", listOf("Cão", "Pão", "Mão")),
        Triple("Lua", "moon", listOf("Lua", "Rua", "Luva")),
        Triple("Rua", "road", listOf("Rua", "Lua", "Luva")),
        Triple("Luva", "glove", listOf("Luva", "Lua", "Rua")),
        Triple("Sapo", "frog", listOf("Sapo", "Saco", "Sapato")),
        Triple("Saco", "bag", listOf("Saco", "Sapo", "Sapato")),
        Triple("Leite", "milk", listOf("Leite", "Peixe", "Dente")),
        Triple("Peixe", "fish", listOf("Peixe", "Leite", "Dente")),
        Triple("Dente", "tooth", listOf("Dente", "Leite", "Peixe")),
        Triple("Mesa", "table", listOf("Mesa", "Casa", "Mala")),
        Triple("Casa", "house", listOf("Casa", "Mesa", "Mala")),
        Triple("Mala", "suitcase", listOf("Mala", "Casa", "Mesa")),
        Triple("Bola", "ball", listOf("Bola", "Bolo", "Balão")),
        Triple("Bolo", "cake", listOf("Bolo", "Bola", "Balão"))
    ).map { (word, symbol, choices) ->
        LearningQuestion("Lê e escolhe a palavra correspondente à imagem.", symbol, choices.sorted(), word)
    }

    fun readingQuestions(): List<LearningQuestion> = reading

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
