package com.vcc.aprender

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.data.progress.LearningProgress
import com.vcc.aprender.ui.progress.ProgressScreen
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProgressScreenUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun emptyProgressShowsHelpfulMessage() {
        composeRule.setContent { ProgressScreen(emptyList(), onBack = {}) }
        composeRule.onNodeWithText("Evolução da criança").assertIsDisplayed()
        composeRule.onNodeWithText("Ainda não existem resultados. Experimenta uma atividade para começar!").assertIsDisplayed()
    }

    @Test fun progressShowsSummaryAndBackNavigation() {
        var back = false
        composeRule.setContent {
            ProgressScreen(
                listOf(LearningProgress(profileId = 1, topicId = "ANIMALS", correctAnswers = 3, totalAnswers = 4)),
                onBack = { back = true }
            )
        }
        composeRule.onAllNodesWithText("Taxa de acerto: 75%").onFirst().assertIsDisplayed()
        composeRule.onNodeWithText("Voltar ao início").performClick()
        composeRule.runOnIdle { assertTrue(back) }
    }
}
