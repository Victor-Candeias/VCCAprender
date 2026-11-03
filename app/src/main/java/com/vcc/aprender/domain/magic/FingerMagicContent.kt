package com.vcc.aprender.domain.magic

import com.vcc.aprender.domain.model.LearningTopic

enum class MagicPicture(val label: String) {
    CAT("Gato"), DOG("Cão"), BIRD("Pássaro"), FISH("Peixe"),
    APPLE("Maçã"), BANANA("Banana"), CARROT("Cenoura"), STRAWBERRY("Morango"),
    RED("Vermelho"), BLUE("Azul"), YELLOW("Amarelo"),
    CIRCLE("Círculo"), TRIANGLE("Triângulo"), SQUARE("Quadrado"),
    SUN("Sol"), CLOUD("Nuvem"), SNOW("Floco de neve"), UMBRELLA("Guarda-chuva"),
    RAINBOW("Arco-íris"), TREE("Árvore"), FLOWER("Flor"), BALL("Bola"), BOOK("Livro"), CLOCK("Relógio")
}

data class MagicQuestion(
    val id: String,
    val prompt: String,
    val options: List<MagicPicture>,
    val correctIndex: Int,
    val explanation: String,
    val topic: LearningTopic
) {
    init {
        require(id.isNotBlank() && prompt.isNotBlank() && explanation.isNotBlank())
        require(options.size == 3 && options.distinct().size == 3)
        require(correctIndex in options.indices)
    }
}

object FingerMagicContent {
    const val REVEAL_MILLIS = 800L
    const val WRONG_VISIBLE_MILLIS = 3_000L

    val questions: List<MagicQuestion> = listOf(
        MagicQuestion("magic-01", "Qual destes animais faz miau?", listOf(MagicPicture.CAT, MagicPicture.DOG, MagicPicture.BIRD), 0, "O gato faz miau!", LearningTopic.ANIMALS),
        MagicQuestion("magic-02", "Qual destes animais vive na água?", listOf(MagicPicture.CAT, MagicPicture.FISH, MagicPicture.DOG), 1, "O peixe vive na água!", LearningTopic.ANIMALS),
        MagicQuestion("magic-03", "Qual destes animais tem penas?", listOf(MagicPicture.DOG, MagicPicture.FISH, MagicPicture.BIRD), 2, "O pássaro tem penas!", LearningTopic.ANIMALS),
        MagicQuestion("magic-04", "Qual destes animais faz ão-ão?", listOf(MagicPicture.BIRD, MagicPicture.DOG, MagicPicture.CAT), 1, "O cão faz ão-ão!", LearningTopic.SOUNDS),
        MagicQuestion("magic-05", "Qual destes frutos é comprido e amarelo?", listOf(MagicPicture.BANANA, MagicPicture.STRAWBERRY, MagicPicture.APPLE), 0, "A banana é comprida e amarela!", LearningTopic.WORDS),
        MagicQuestion("magic-06", "Qual destes alimentos é uma raiz cor de laranja?", listOf(MagicPicture.APPLE, MagicPicture.BANANA, MagicPicture.CARROT), 2, "A cenoura é uma raiz cor de laranja!", LearningTopic.WORDS),
        MagicQuestion("magic-07", "Qual destes frutos tem pequenas sementes à vista por fora?", listOf(MagicPicture.BANANA, MagicPicture.STRAWBERRY, MagicPicture.APPLE), 1, "O morango tem pequenas sementes à vista por fora!", LearningTopic.WORDS),
        MagicQuestion("magic-08", "Qual destas cores é o azul?", listOf(MagicPicture.BLUE, MagicPicture.YELLOW, MagicPicture.RED), 0, "Encontraste o azul!", LearningTopic.COLORS),
        MagicQuestion("magic-09", "Qual destas cores é o vermelho?", listOf(MagicPicture.YELLOW, MagicPicture.BLUE, MagicPicture.RED), 2, "Encontraste o vermelho!", LearningTopic.COLORS),
        MagicQuestion("magic-10", "Qual destas cores é o amarelo?", listOf(MagicPicture.RED, MagicPicture.YELLOW, MagicPicture.BLUE), 1, "Encontraste o amarelo!", LearningTopic.COLORS),
        MagicQuestion("magic-11", "Qual destas formas é redonda e não tem cantos?", listOf(MagicPicture.CIRCLE, MagicPicture.SQUARE, MagicPicture.TRIANGLE), 0, "O círculo é redondo e não tem cantos!", LearningTopic.SHAPES),
        MagicQuestion("magic-12", "Qual destas formas tem três lados?", listOf(MagicPicture.SQUARE, MagicPicture.CIRCLE, MagicPicture.TRIANGLE), 2, "O triângulo tem três lados!", LearningTopic.SHAPES),
        MagicQuestion("magic-13", "Qual destas formas tem quatro lados iguais?", listOf(MagicPicture.TRIANGLE, MagicPicture.SQUARE, MagicPicture.CIRCLE), 1, "O quadrado tem quatro lados iguais!", LearningTopic.SHAPES),
        MagicQuestion("magic-14", "O que usamos para nos proteger da chuva?", listOf(MagicPicture.UMBRELLA, MagicPicture.BALL, MagicPicture.BOOK), 0, "Usamos um guarda-chuva!", LearningTopic.WORDS),
        MagicQuestion("magic-15", "Qual destas imagens mostra o Sol?", listOf(MagicPicture.CLOUD, MagicPicture.SNOW, MagicPicture.SUN), 2, "O Sol ilumina e aquece a Terra!", LearningTopic.WORDS),
        MagicQuestion("magic-16", "O que tem muitas cores e pode aparecer no céu depois da chuva?", listOf(MagicPicture.SUN, MagicPicture.RAINBOW, MagicPicture.CLOUD), 1, "É o arco-íris!", LearningTopic.WORDS),
        MagicQuestion("magic-17", "Qual destas plantas tem um tronco de madeira?", listOf(MagicPicture.TREE, MagicPicture.FLOWER, MagicPicture.CARROT), 0, "A árvore tem um tronco de madeira!", LearningTopic.WORDS),
        MagicQuestion("magic-18", "Com qual destes objetos podemos jogar futebol?", listOf(MagicPicture.BOOK, MagicPicture.UMBRELLA, MagicPicture.BALL), 2, "Jogamos futebol com uma bola!", LearningTopic.WORDS),
        MagicQuestion("magic-19", "Qual destes objetos tem páginas para ler histórias?", listOf(MagicPicture.CLOCK, MagicPicture.BOOK, MagicPicture.BALL), 1, "O livro tem páginas para ler histórias!", LearningTopic.WORDS),
        MagicQuestion("magic-20", "Qual destes objetos serve para ver as horas?", listOf(MagicPicture.CLOCK, MagicPicture.BALL, MagicPicture.BOOK), 0, "O relógio mostra as horas!", LearningTopic.WORDS)
    )
}
