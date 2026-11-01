package com.vcc.aprender

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.ui.home.HomeScreen
import com.vcc.aprender.ui.home.HomeUiState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun topicsAreVisibleAndClickable() {
        var selected: LearningTopic? = null
        composeRule.setContent {
            HomeScreen(
                state = HomeUiState.Success(listOf(LearningTopic.ANIMALS, LearningTopic.WORDS)),
                onRetry = {},
                onTopicClick = { selected = it }
            )
        }
        composeRule.onNodeWithText("VCCAprender").assertIsDisplayed()
        composeRule.onNodeWithText(LearningTopic.ANIMALS.title).performScrollTo().performClick()
        composeRule.runOnIdle { assertEquals(LearningTopic.ANIMALS, selected) }
    }

    @Test fun errorOffersRetry() {
        var retried = false
        composeRule.setContent {
            HomeScreen(
                state = HomeUiState.Error("Falha ao carregar"),
                onRetry = { retried = true },
                onTopicClick = {}
            )
        }
        composeRule.onNodeWithText("Falha ao carregar").assertIsDisplayed()
        composeRule.onNodeWithText("Tentar novamente").performClick()
        composeRule.runOnIdle { assertEquals(true, retried) }
    }
}
