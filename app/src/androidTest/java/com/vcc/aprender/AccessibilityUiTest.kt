package com.vcc.aprender

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vcc.aprender.domain.cards.RevealTiles
import com.vcc.aprender.ui.cards.RevealTilesGrid
import com.vcc.aprender.ui.components.FamilyHeader
import com.vcc.aprender.ui.components.GameAnswerButton
import com.vcc.aprender.ui.components.GameNavigation
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccessibilityUiTest {
    @get:Rule val composeRule = createComposeRule()

    @Test fun answersAnnounceTheChosenStateWithoutDependingOnColour() {
        composeRule.setContent {
            Column {
                GameAnswerButton(
                    text = "Gato", prefix = "A)", selected = true,
                    correct = true, enabled = false, onClick = {}
                )
                GameAnswerButton(
                    text = "Cão", prefix = "B)", selected = true,
                    correct = false, enabled = false, onClick = {}
                )
            }
        }
        composeRule.onNodeWithContentDescription("A) Gato").assert(
            SemanticsMatcher.expectValue(
                SemanticsProperties.StateDescription, "Selecionada, resposta correta"
            )
        )
        composeRule.onNodeWithContentDescription("B) Cão").assert(
            SemanticsMatcher.expectValue(
                SemanticsProperties.StateDescription, "Selecionada, tenta novamente"
            )
        )
    }

    @Test fun aKeyboardEnterActivatesTheFocusedAnswer() {
        var count = 0
        composeRule.setContent {
            val requester = remember { FocusRequester() }
            GameAnswerButton(
                text = "Gato", prefix = "A)", enabled = true,
                onClick = { count++ },
                modifier = Modifier.focusRequester(requester)
            )
            LaunchedEffect(requester) { requester.requestFocus() }
        }
        composeRule.onNodeWithContentDescription("A) Gato").assertIsFocused()
        composeRule.onNodeWithContentDescription("A) Gato").performKeyInput {
            pressKey(Key.Enter)
        }
        composeRule.runOnIdle { assertEquals(1, count) }
    }

    @Test fun familyTitlesAreHeadingsAndDecorativeArtworkIsNotRepeated() {
        composeRule.setContent {
            FamilyHeader("📊", "Evolução", "O teu progresso")
        }
        composeRule.onNodeWithText("Evolução").assert(
            SemanticsMatcher.keyIsDefined(SemanticsProperties.Heading)
        )
        composeRule.onNodeWithContentDescription("Evolução").assertDoesNotExist()
    }

    @Test fun largeFontsKeepNextAndPreviousActionsReachable() {
        composeRule.setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(density = 1f, fontScale = 2f)
            ) {
                Box(Modifier.requiredSize(width = 320.dp, height = 500.dp)) {
                    GameNavigation(onPrevious = {}, onNext = {}, nextLabel = "Próxima pergunta")
                }
            }
        }
        composeRule.onNodeWithText("Anterior").assertIsDisplayed().assertHasClickAction()
        composeRule.onNodeWithText("Próxima pergunta").assertIsDisplayed().assertHasClickAction()
    }

    @Test fun switchAccessCanUseRevealTilesAsButtons() {
        var revealed = -1
        composeRule.setContent {
            Box(Modifier.requiredSize(340.dp, 430.dp)) {
                RevealTilesGrid(
                    illustration = "🐱",
                    tiles = RevealTiles(),
                    enabled = true,
                    onReveal = { revealed = it }
                )
            }
        }
        composeRule.onNodeWithContentDescription("Revelar quadrado 1 de 9")
            .assertHasClickAction()
            .assert(
                SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button)
            )
            .performClick()
        composeRule.runOnIdle { assertEquals(0, revealed) }
    }
}
