package com.vcc.aprender.domain.model

enum class LearningTopic(val title: String, val description: String) {
    LETTERS("Letras", "Descobre as letras e as palavras"),
    NUMBERS("Números", "Aprende a contar"),
    COLORS("Cores", "Explora as cores"),
    SHAPES("Formas", "Conhece as formas"),
    ANIMALS("Animais", "Descobre os animais"),
    SOUNDS("Sons", "Reconhece os sons dos animais"),
    WORDS("Palavras", "Aprende palavras do dia a dia")
}
