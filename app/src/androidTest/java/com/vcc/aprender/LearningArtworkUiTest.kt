package com.vcc.aprender

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.test.core.app.ApplicationProvider
import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.domain.cards.RevealTiles
import com.vcc.aprender.domain.model.LearningIllustration
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.ui.activities.ActivityScreen
import com.vcc.aprender.ui.activities.ActivityUiState
import com.vcc.aprender.ui.cards.RevealTilesGrid
import com.vcc.aprender.ui.components.LearningArtwork
import com.vcc.aprender.ui.components.drawable
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LearningArtworkUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun everyVectorLoadsOnAndroid() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        LearningIllustration.entries.forEach { artwork ->
            assertNotNull(artwork.key, context.getDrawable(artwork.drawable()))
        }
    }

    @Test fun countingPictureChangesWithTheQuestion() {
        val value = mutableStateOf("stars:3")
        composeRule.setContent { LearningArtwork(value.value) }
        composeRule.onNodeWithContentDescription("3 estrelas").assertIsDisplayed()
        composeRule.runOnIdle { value.value = "stars:10" }
        composeRule.onNodeWithContentDescription("10 estrelas").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("3 estrelas").assertDoesNotExist()
    }

    @Test fun activitiesDisplayTheActualAnimalAndStillAcceptAnswers() {
        var selected: String? = null
        composeRule.setContent {
            ActivityScreen(ActivityUiState(LearningTopic.ANIMALS),
                onAnswer = { selected = it }, onPrevious = {}, onNext = {}, onBack = {})
        }
        composeRule.onNode(hasContentDescription("Gato") and !hasClickAction()).assertIsDisplayed()
        composeRule.onNodeWithText("Gato").performClick()
        composeRule.runOnIdle { org.junit.Assert.assertEquals("Gato", selected) }
    }

    @Test fun coveredPictureIsNotAnnouncedBeforeItIsRevealed() {
        val tiles = mutableStateOf(RevealTiles())
        composeRule.setContent {
            RevealTilesGrid("apple", tiles.value, true, onReveal = { index ->
                tiles.value = tiles.value.reveal(index)
            })
        }
        composeRule.onNodeWithContentDescription("Maçã").assertDoesNotExist()
        repeat(tiles.value.total) { index ->
            composeRule.onNodeWithContentDescription("Revelar quadrado ${index + 1} de ${tiles.value.total}").performClick()
        }
        composeRule.onNodeWithContentDescription("Maçã").assertIsDisplayed()
    }

    @Test fun customTextIllustrationsStillRender() {
        composeRule.setContent { LearningArtwork("O meu desenho") }
        composeRule.onNodeWithText("O meu desenho").assertIsDisplayed()
    }
}
