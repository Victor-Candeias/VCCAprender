package com.vcc.aprender

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.data.cards.CardJsonCodec
import com.vcc.aprender.data.cards.CustomCardEntity
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CardJsonCodecInstrumentedTest {
    private val card = CustomCardEntity(
        id = 15, topic = "ANIMALS", difficulty = "MEDIUM",
        question = "Que animal faz miau?", firstOption = "Cão",
        secondOption = "Gato", thirdOption = "Vaca", correctIndex = 1
    )

    @Test fun roundTripPreservesEducationalContentWithoutDatabaseId() {
        val json = CardJsonCodec.encode(listOf(card))
        val restored = CardJsonCodec.decode(json)
        assertEquals(1, restored.size)
        assertEquals("Que animal faz miau?", restored.single().question)
        assertEquals("Gato", restored.single().options[1])
        assertEquals(1, restored.single().correctIndex)
        assertFalse(json.contains("\"id\":15"))
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsUnsupportedVersion() {
        CardJsonCodec.decode("""{"format":"vcc-aprender-cards","version":999,"cards":[]}""")
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsInvalidTopic() {
        CardJsonCodec.decode("""{"format":"vcc-aprender-cards","version":1,"cards":[{"topic":"INVALID","difficulty":"EASY","question":"Teste","options":["A","B","C"],"correctIndex":0}]}""")
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsDuplicateAnswers() {
        CardJsonCodec.decode("""{"format":"vcc-aprender-cards","version":1,"cards":[{"topic":"ANIMALS","difficulty":"EASY","question":"Teste","options":["A","a","C"],"correctIndex":0}]}""")
    }
}
