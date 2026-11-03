package com.vcc.aprender

import com.vcc.aprender.data.cards.OfflineCardCatalog
import com.vcc.aprender.domain.activities.ActivityContent
import com.vcc.aprender.domain.alphabet.AlphabetContent
import com.vcc.aprender.domain.model.LearningIllustration
import com.vcc.aprender.domain.model.LearningPicture
import com.vcc.aprender.domain.model.LearningTopic
import org.junit.Assert.*
import org.junit.Test

class LearningIllustrationTest {
    @Test fun everyBuiltInQuestionAndOptionHasOfflineArtwork() {
        val values = LearningTopic.entries.flatMap { ActivityContent.questions(it) }.map { it.illustration } +
            ActivityContent.readingQuestions().map { it.illustration } +
            AlphabetContent.cards.map { it.illustration } +
            OfflineCardCatalog.cards.flatMap { listOfNotNull(it.illustration) + it.options.mapNotNull { o -> o.illustration } }
        values.forEach { value -> assertNotNull("Missing artwork for $value", LearningPicture.from(value)) }
    }

    @Test fun allTenCountingQuestionsKeepTheirExactStarCount() {
        ActivityContent.questions(LearningTopic.NUMBERS).forEach { question ->
            val picture = LearningPicture.from(question.illustration)!!
            assertEquals(LearningIllustration.STAR, picture.artwork)
            assertEquals(question.correctAnswer.toInt(), picture.count)
        }
        assertEquals(3, LearningPicture.from(OfflineCardCatalog.find("numbers-three")!!.illustration!!)!!.count)
    }

    @Test fun pictureLabelsMatchTheObjectBeingAskedAbout() {
        listOf(LearningTopic.COLORS, LearningTopic.SHAPES, LearningTopic.ANIMALS, LearningTopic.WORDS).forEach { topic ->
            ActivityContent.questions(topic).forEach { question ->
                assertEquals(question.correctAnswer, LearningPicture.from(question.illustration)!!.description)
            }
        }
        ActivityContent.readingQuestions().forEach { question ->
            assertEquals(question.correctAnswer, LearningPicture.from(question.illustration)!!.description)
        }
        AlphabetContent.cards.forEach { card ->
            assertEquals(card.word, LearningPicture.from(card.illustration)!!.description)
        }
        assertEquals("table", ActivityContent.readingQuestions().first { it.correctAnswer == "Mesa" }.illustration)
    }

    @Test fun legacyPicturesAreRecognisedAndCustomTextIsNotLost() {
        assertEquals(LearningPicture.from("stars:3"), LearningPicture.from("⭐⭐⭐"))
        assertEquals(LearningPicture.from("sun"), LearningPicture.from("☀️"))
        assertEquals(LearningPicture.from("flower"), LearningPicture.from("🌼"))
        assertNull(LearningPicture.from("O meu desenho"))
        // A user's chair illustration must not become a table because an old built-in question was wrong.
        assertNull(LearningPicture.from("🪑"))
        listOf("stars:0", "stars:11", "stars:x", "").forEach { assertNull(LearningPicture.from(it)) }
    }
}
