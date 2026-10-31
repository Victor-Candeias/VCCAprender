package com.vcc.aprender.data.cards

import com.vcc.aprender.domain.cards.CardDifficulty
import com.vcc.aprender.domain.cards.CardOption
import com.vcc.aprender.domain.cards.DiscoverCard
import com.vcc.aprender.domain.model.LearningTopic

object OfflineCardCatalog {
    val cards: List<DiscoverCard> = listOf(
        DiscoverCard(
            id = "letters-a", topic = LearningTopic.LETTERS,
            question = "Qual destas palavras começa pela letra A?",
            illustration = "🔤",
            options = listOf(CardOption("a", "Abelha", "🐝"), CardOption("b", "Bola", "⚽"), CardOption("c", "Casa", "🏠")),
            correctOptionId = "a", explanation = "Abelha começa pela letra A."
        ),
        DiscoverCard(
            id = "numbers-three", topic = LearningTopic.NUMBERS,
            question = "Quantas estrelas estão aqui?",
            illustration = "⭐⭐⭐",
            options = listOf(CardOption("2", "Dois"), CardOption("3", "Três"), CardOption("4", "Quatro")),
            correctOptionId = "3", explanation = "Há três estrelas."
        ),
        DiscoverCard(
            id = "colors-blue", topic = LearningTopic.COLORS,
            question = "Qual é a cor deste círculo?",
            illustration = "🔵",
            options = listOf(CardOption("red", "Vermelho"), CardOption("blue", "Azul"), CardOption("green", "Verde")),
            correctOptionId = "blue", explanation = "O círculo é azul."
        ),
        DiscoverCard(
            id = "shapes-triangle", topic = LearningTopic.SHAPES,
            question = "Que forma está representada?",
            illustration = "▲",
            options = listOf(CardOption("circle", "Círculo"), CardOption("square", "Quadrado"), CardOption("triangle", "Triângulo")),
            correctOptionId = "triangle", explanation = "A figura tem três lados."
        ),
        DiscoverCard(
            id = "animals-cat", topic = LearningTopic.ANIMALS,
            question = "Qual destes animais faz miau?",
            illustration = "🐱",
            options = listOf(CardOption("dog", "Cão", "🐶"), CardOption("cat", "Gato", "🐱"), CardOption("cow", "Vaca", "🐄")),
            correctOptionId = "cat", difficulty = CardDifficulty.EASY, explanation = "O gato faz miau."
        ),
        DiscoverCard(
            id = "sounds-dog", topic = LearningTopic.SOUNDS,
            question = "Que animal faz au-au?",
            illustration = "🔊",
            options = listOf(CardOption("dog", "Cão", "🐶"), CardOption("cat", "Gato", "🐱"), CardOption("duck", "Pato", "🦆")),
            correctOptionId = "dog", explanation = "O cão faz au-au."
        ),
        DiscoverCard(
            id = "words-apple", topic = LearningTopic.WORDS,
            question = "Qual é a palavra desta imagem?",
            illustration = "🍎",
            options = listOf(CardOption("apple", "Maçã"), CardOption("pear", "Pera"), CardOption("banana", "Banana")),
            correctOptionId = "apple", explanation = "Esta fruta é uma maçã."
        )
    )

    init {
        require(cards.map { it.id }.distinct().size == cards.size) { "Existem cartões com identificadores repetidos." }
    }

    fun byTopic(topic: LearningTopic): List<DiscoverCard> = cards.filter { it.topic == topic }

    fun find(id: String): DiscoverCard? = cards.firstOrNull { it.id == id }
}
