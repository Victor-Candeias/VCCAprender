package com.vcc.aprender.data.cards

import com.vcc.aprender.domain.cards.CardDifficulty
import com.vcc.aprender.domain.model.LearningTopic
import org.json.JSONArray
import org.json.JSONObject

/** Portable format: database identifiers are deliberately not exported. */
object CardJsonCodec {
    const val VERSION = 1
    const val MAX_CARDS = 500
    const val MAX_BYTES = 1_048_576

    fun encode(cards: List<CustomCardEntity>): String {
        require(cards.size <= MAX_CARDS) { "Demasiados cartões para exportar." }
        val items = JSONArray()
        cards.forEach { card ->
            items.put(JSONObject().apply {
                put("topic", card.topic)
                put("difficulty", card.difficulty)
                put("question", card.question)
                put("options", JSONArray().apply {
                    put(card.firstOption); put(card.secondOption); put(card.thirdOption)
                })
                put("correctIndex", card.correctIndex)
            })
        }
        return JSONObject().put("format", "vcc-aprender-cards")
            .put("version", VERSION).put("cards", items).toString(2)
    }

    fun decode(json: String): List<CardDraft> {
        require(json.toByteArray(Charsets.UTF_8).size <= MAX_BYTES) { "O ficheiro excede 1 MB." }
        val root = JSONObject(json)
        require(root.getString("format") == "vcc-aprender-cards") { "Formato JSON não reconhecido." }
        require(root.getInt("version") == VERSION) { "Versão JSON não suportada." }
        val items = root.getJSONArray("cards")
        require(items.length() <= MAX_CARDS) { "O ficheiro tem demasiados cartões." }
        return (0 until items.length()).map { index ->
            val item = items.getJSONObject(index)
            val topic = LearningTopic.entries.firstOrNull { it.name == item.getString("topic") }
                ?: throw IllegalArgumentException("Categoria inválida no cartão ${index + 1}.")
            val difficulty = CardDifficulty.entries.firstOrNull { it.name == item.getString("difficulty") }
                ?: throw IllegalArgumentException("Dificuldade inválida no cartão ${index + 1}.")
            val options = item.getJSONArray("options")
            require(options.length() == 3) { "O cartão ${index + 1} deve ter três respostas." }
            CardDraft(
                topic, difficulty, item.getString("question"),
                (0 until 3).map { options.getString(it) }, item.getInt("correctIndex")
            ).validated().also { draft ->
                require(draft.question.length <= 200 && draft.options.all { it.length <= 80 }) {
                    "Texto demasiado longo no cartão ${index + 1}."
                }
            }
        }
    }
}
