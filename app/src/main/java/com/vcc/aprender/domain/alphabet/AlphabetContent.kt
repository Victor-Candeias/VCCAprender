package com.vcc.aprender.domain.alphabet

data class LetterCard(val letter: Char, val word: String, val illustration: String)

object AlphabetContent {
    val cards: List<LetterCard> = listOf(
        LetterCard('A', "Abelha", "bee"), LetterCard('B', "Bola", "ball"),
        LetterCard('C', "Casa", "house"), LetterCard('D', "Dado", "die"),
        LetterCard('E', "Elefante", "elephant"), LetterCard('F', "Flor", "flower"),
        LetterCard('G', "Gato", "cat"), LetterCard('H', "Helicóptero", "helicopter"),
        LetterCard('I', "Ilha", "island"), LetterCard('J', "Janela", "window"),
        LetterCard('K', "Kiwi", "kiwi"), LetterCard('L', "Lua", "moon"),
        LetterCard('M', "Maçã", "apple"), LetterCard('N', "Navio", "ship"),
        LetterCard('O', "Ovelha", "sheep"), LetterCard('P', "Pato", "duck"),
        LetterCard('Q', "Queijo", "cheese"), LetterCard('R', "Rato", "mouse"),
        LetterCard('S', "Sol", "sun"), LetterCard('T', "Tartaruga", "turtle"),
        LetterCard('U', "Uva", "grapes"), LetterCard('V', "Vaca", "cow"),
        LetterCard('W', "Waffle", "waffle"), LetterCard('X', "Xilofone", "xylophone"),
        LetterCard('Y', "Yoga", "yoga"), LetterCard('Z', "Zebra", "zebra")
    )

    fun optionsFor(index: Int): List<Char> {
        require(index in cards.indices)
        val correct = cards[index].letter
        val other = cards[(index + 7) % cards.size].letter
        val third = cards[(index + 13) % cards.size].letter
        return listOf(correct, other, third).sorted()
    }
}
