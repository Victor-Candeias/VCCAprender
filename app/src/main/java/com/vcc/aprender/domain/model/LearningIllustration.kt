package com.vcc.aprender.domain.model

/** Stable offline artwork keys. Labels describe the actual picture for non-visual learners. */
enum class LearningIllustration(val key: String, val label: String, val legacy: String?) {
    STAR("star", "Estrela", "⭐"),
    RED("red", "Vermelho", "🔴"), BLUE("blue", "Azul", "🔵"),
    GREEN("green", "Verde", "🟢"), YELLOW("yellow", "Amarelo", "🟡"),
    ORANGE("orange", "Laranja", "🟠"), PURPLE("purple", "Roxo", "🟣"),
    CIRCLE("circle", "Círculo", "●"), SQUARE("square", "Quadrado", "■"),
    TRIANGLE("triangle", "Triângulo", "▲"), RECTANGLE("rectangle", "Retângulo", "▰"),
    HEART("heart", "Coração", "♥"), CAT("cat", "Gato", "🐱"), DOG("dog", "Cão", "🐶"),
    COW("cow", "Vaca", "🐄"), LION("lion", "Leão", "🦁"),
    ELEPHANT("elephant", "Elefante", "🐘"), BUTTERFLY("butterfly", "Borboleta", "🦋"),
    FISH("fish", "Peixe", "🐟"), RABBIT("rabbit", "Coelho", "🐰"),
    SHEEP("sheep", "Ovelha", "🐑"), DUCK("duck", "Pato", "🦆"),
    ROOSTER("rooster", "Galo", "🐓"), MOUSE("mouse", "Rato", "🐭"), FROG("frog", "Sapo", "🐸"),
    BEE("bee", "Abelha", "🐝"), TURTLE("turtle", "Tartaruga", "🐢"), ZEBRA("zebra", "Zebra", "🦓"),
    HOUSE("house", "Casa", "🏠"), BALL("ball", "Bola", "⚽"), BOOK("book", "Livro", "📚"),
    APPLE("apple", "Maçã", "🍎"), CAR("car", "Carro", "🚗"), SUN("sun", "Sol", "☀"),
    FLOWER("flower", "Flor", "🌸"), KEY("key", "Chave", "🔑"), DIE("die", "Dado", "🎲"),
    FINGER("finger", "Dedo", "☝"), HAND("hand", "Mão", "✋"), BREAD("bread", "Pão", "🍞"),
    MOON("moon", "Lua", "🌙"), ROAD("road", "Rua", "🛣"), GLOVE("glove", "Luva", "🧤"),
    BAG("bag", "Saco", "🛍"), MILK("milk", "Leite", "🥛"), TOOTH("tooth", "Dente", "🦷"),
    TABLE("table", "Mesa", null), SUITCASE("suitcase", "Mala", "🧳"), CAKE("cake", "Bolo", "🎂"),
    HELICOPTER("helicopter", "Helicóptero", "🚁"), ISLAND("island", "Ilha", "🏝"),
    WINDOW("window", "Janela", "🪟"), KIWI("kiwi", "Kiwi", "🥝"), SHIP("ship", "Navio", "🚢"),
    CHEESE("cheese", "Queijo", "🧀"), GRAPES("grapes", "Uva", "🍇"),
    WAFFLE("waffle", "Waffle", "🧇"), XYLOPHONE("xylophone", "Xilofone", "🎼"),
    YOGA("yoga", "Yoga", "🧘"), LETTERS("letters", "Letras", "🔤"), SOUND("sound", "Som", "🔊");

    companion object {
        fun from(value: String): LearningIllustration? {
            val normalized = value.replace("\uFE0F", "")
            return entries.firstOrNull { it.key == normalized || it.legacy == normalized }
                ?: when (normalized) {
                    "★" -> STAR
                    "🌼" -> FLOWER
                    else -> null
                }
        }
    }
}

data class LearningPicture(val artwork: LearningIllustration, val count: Int = 1) {
    init { require(count in 1..10) }
    val description: String get() = if (count == 1) artwork.label else "$count estrelas"

    companion object {
        /** Also understands historical illustrations without changing saved card/profile data. */
        fun from(value: String): LearningPicture? {
            if (value.startsWith("stars:")) {
                val count = value.substringAfter(':').toIntOrNull() ?: return null
                return if (count in 1..10) LearningPicture(LearningIllustration.STAR, count) else null
            }
            if (value.isNotEmpty() && value.all { it == '⭐' }) {
                return if (value.length <= 10) LearningPicture(LearningIllustration.STAR, value.length) else null
            }
            return LearningIllustration.from(value)?.let { LearningPicture(it) }
        }
    }
}
