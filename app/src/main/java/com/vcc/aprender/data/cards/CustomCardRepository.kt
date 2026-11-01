package com.vcc.aprender.data.cards

import com.vcc.aprender.domain.cards.CardDifficulty
import com.vcc.aprender.domain.cards.CardOption
import com.vcc.aprender.domain.cards.DiscoverCard
import com.vcc.aprender.domain.model.LearningTopic
import kotlinx.coroutines.flow.Flow

data class CardDraft(
    val topic: LearningTopic,
    val difficulty: CardDifficulty,
    val question: String,
    val options: List<String>,
    val correctIndex: Int
) {
    fun validated(): CardDraft {
        require(question.isNotBlank()) { "Escreve uma pergunta." }
        require(options.size == 3 && options.all { it.isNotBlank() }) { "Preenche as três respostas." }
        require(options.map { it.trim().lowercase() }.distinct().size == 3) { "As respostas devem ser diferentes." }
        require(correctIndex in 0..2) { "Escolhe a resposta correta." }
        return copy(question = question.trim(), options = options.map { it.trim() })
    }
}

class CustomCardRepository(private val dao: CustomCardDao) {
    val cards: Flow<List<CustomCardEntity>> = dao.observeAll()

    suspend fun save(draft: CardDraft, id: Long = 0): Long {
        val valid = draft.validated()
        return dao.save(CustomCardEntity(
            id = id, topic = valid.topic.name, difficulty = valid.difficulty.name,
            question = valid.question,
            firstOption = valid.options[0], secondOption = valid.options[1],
            thirdOption = valid.options[2], correctIndex = valid.correctIndex
        ))
    }

    suspend fun delete(id: Long) { require(id > 0); dao.delete(id) }

    suspend fun gameCards(): List<DiscoverCard> = dao.getAll().mapNotNull { it.toDiscoverCard() }
}

fun CustomCardEntity.toDiscoverCard(): DiscoverCard? {
    val parsedTopic = LearningTopic.entries.firstOrNull { it.name == topic } ?: return null
    val parsedDifficulty = CardDifficulty.entries.firstOrNull { it.name == difficulty } ?: return null
    val options = listOf(firstOption, secondOption, thirdOption)
    if (correctIndex !in 0..2 || options.any { it.isBlank() } || question.isBlank()) return null
    return DiscoverCard(
        id = "custom-$id", topic = parsedTopic, question = question,
        options = options.mapIndexed { index, text -> CardOption("option-$index", text) },
        correctOptionId = "option-$correctIndex", difficulty = parsedDifficulty
    )
}
