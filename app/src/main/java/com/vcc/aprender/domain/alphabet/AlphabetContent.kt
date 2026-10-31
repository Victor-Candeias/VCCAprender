package com.vcc.aprender.domain.alphabet

data class LetterCard(val letter: Char, val word: String, val emoji: String)

object AlphabetContent {
    val cards: List<LetterCard> = listOf(
        LetterCard('A', "Abelha", "🐝"), LetterCard('B', "Bola", "⚽"),
        LetterCard('C', "Casa", "🏠"), LetterCard('D', "Dado", "🎲"),
        LetterCard('E', "Elefante", "🐘"), LetterCard('F', "Flor", "🌼"),
        LetterCard('G', "Gato", "🐱"), LetterCard('H', "Helicóptero", "🚁"),
        LetterCard('I', "Ilha", "🏝️"), LetterCard('J', "Janela", "🪟"),
        LetterCard('K', "Kiwi", "🥝"), LetterCard('L', "Lua", "🌙"),
        LetterCard('M', "Maçã", "🍎"), LetterCard('N', "Navio", "🚢"),
        LetterCard('O', "Ovelha", "🐑"), LetterCard('P', "Pato", "🦆"),
        LetterCard('Q', "Queijo", "🧀"), LetterCard('R', "Rato", "🐭"),
        LetterCard('S', "Sol", "☀️"), LetterCard('T', "Tartaruga", "🐢"),
        LetterCard('U', "Uva", "🍇"), LetterCard('V', "Vaca", "🐄"),
        LetterCard('W', "Waffle", "🧇"), LetterCard('X', "Xilofone", "🎼"),
        LetterCard('Y', "Yoga", "🧘"), LetterCard('Z', "Zebra", "🦓")
    )

    fun optionsFor(index: Int): List<Char> {
        require(index in cards.indices)
        val correct = cards[index].letter
        val other = cards[(index + 7) % cards.size].letter
        val third = cards[(index + 13) % cards.size].letter
        return listOf(correct, other, third).sorted()
    }
}
