package com.vcc.aprender

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.room.Room
import com.vcc.aprender.data.profile.ChildProfile
import com.vcc.aprender.data.profile.ProfileDatabase
import com.vcc.aprender.data.progress.ProgressRepository
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.domain.magic.FingerMagicContent
import com.vcc.aprender.domain.magic.FingerMagicSession
import com.vcc.aprender.ui.home.HomeScreen
import com.vcc.aprender.ui.home.HomeUiState
import com.vcc.aprender.ui.magic.FingerMagicScreen
import com.vcc.aprender.ui.magic.FingerMagicViewModel
import com.vcc.aprender.ui.magic.FingerMagicUiState
import com.vcc.aprender.ui.theme.AprenderTheme
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FingerMagicUiTest {
    @get:Rule val compose = createComposeRule()
    private var database: ProfileDatabase? = null

    @After fun closeDatabase() { database?.close() }

    private fun showGame(nanoTime: () -> Long = System::nanoTime): FingerMagicViewModel {
        val db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), ProfileDatabase::class.java).build()
        database = db
        val profileId = runBlocking { db.profileDao().save(ChildProfile(name = "Magia", age = 5, avatar = "🐻")) }
        val model = FingerMagicViewModel(profileId, ProgressRepository(db), nanoTime)
        compose.setContent {
            val state by model.state.collectAsStateWithLifecycle()
            AprenderTheme {
                FingerMagicScreen(state, { model.press(it) }, model::releaseTouch,
                    { model.press(it, accessible = true) }, model::cancelTouch,
                    model::next, model::restart, {})
            }
        }
        return model
    }

    @Test fun homeHasDedicatedGameEntry() {
        var opened = false
        compose.setContent {
            AprenderTheme {
                HomeScreen(HomeUiState.Success(LearningTopic.entries), {}, {}, { opened = true })
            }
        }
        compose.onNodeWithTag("home-finger-magic").performScrollTo().performClick()
        compose.runOnIdle { assertTrue(opened) }
    }

    @Test fun shortPressCancelsAndFullPressKeepsCorrectSmileUntilNext() {
        val model = showGame()
        val correct = model.state.value.session.question.correctIndex
        val square = compose.onNodeWithTag("magic-square-$correct")
        square.performScrollTo().performTouchInput { down(center); advanceEventTime(250); up() }
        compose.runOnIdle {
            assertFalse(model.state.value.session.solved)
            assertTrue(model.state.value.session.attempted.isEmpty())
        }
        square.performTouchInput { down(center) }
        compose.waitUntil(4_000) { model.state.value.session.solved }
        square.performTouchInput { up() }
        square.assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Sorriso. Resposta correta"))
        // A completed square stays revealed well beyond the wrong-answer timeout.
        val start = System.nanoTime()
        compose.waitUntil(5_000) { (System.nanoTime() - start) / 1_000_000 > 3_300 }
        compose.runOnIdle { assertTrue(model.state.value.session.solved); assertEquals(0, model.state.value.session.index) }
        square.assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Sorriso. Resposta correta"))
        compose.onNodeWithText("Seguinte").performScrollTo().performClick()
        compose.runOnIdle { assertEquals(1, model.state.value.session.index); assertFalse(model.state.value.session.solved) }
    }

    @Test fun wrongSmileRemainsWhileHeldThenHidesThreeSecondsAfterRelease() {
        val model = showGame()
        val wrong = (model.state.value.session.question.correctIndex + 1) % 3
        val square = compose.onNodeWithTag("magic-square-$wrong")
        square.performScrollTo().performTouchInput { down(center) }
        compose.waitUntil(4_000) { wrong in model.state.value.session.visibleWrong }
        val heldSince = System.nanoTime()
        compose.waitUntil(5_000) { (System.nanoTime() - heldSince) / 1_000_000 > 3_300 }
        compose.runOnIdle { assertTrue(wrong in model.state.value.session.visibleWrong) }
        square.performTouchInput { up() }
        compose.runOnIdle { assertTrue(wrong in model.state.value.session.visibleWrong) }
        val releasedAt = System.nanoTime()
        compose.waitUntil(3_000) { (System.nanoTime() - releasedAt) / 1_000_000 > 2_300 }
        compose.runOnIdle { assertTrue(wrong in model.state.value.session.visibleWrong) }
        square.assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Cara triste. Tenta outra resposta"))
        compose.onNodeWithText("Seguinte").assertDoesNotExist()
        compose.waitUntil(4_000) { model.state.value.session.visibleWrong.isEmpty() }
        compose.runOnIdle { assertFalse(model.state.value.session.solved) }
        square.assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Por revelar"))
    }

    @Test fun accessibilityClickRevealsWithoutPhysicalHoldAndCancellationDoesNotAnswer() {
        val model = showGame()
        val correct = model.state.value.session.question.correctIndex
        compose.runOnIdle { model.press(correct); model.cancelTouch() }
        compose.runOnIdle { assertNull(model.state.value.heldOption); assertFalse(model.state.value.session.solved) }
        compose.onNodeWithTag("magic-square-$correct").performScrollTo()
            .performSemanticsAction(SemanticsActions.OnClick) { it() }
        compose.waitUntil(4_000) { model.state.value.session.solved }
        compose.runOnIdle { assertNull(model.state.value.heldOption) }
    }

    @Test fun releaseAtRevealDeadlineKeepsResultEvenBeforeNextTimerTick() {
        var nowNanos = 0L
        val model = showGame { nowNanos }
        val correct = model.state.value.session.question.correctIndex
        compose.runOnIdle {
            model.press(correct)
            nowNanos = 1_999_999_999L
            model.releaseTouch(correct)
            assertFalse(model.state.value.session.solved)
            assertTrue(model.state.value.session.attempted.isEmpty())

            model.press(correct)
            nowNanos += 1_000_000_000L
            model.releaseTouch(correct)
            assertTrue(model.state.value.session.solved)
            assertNull(model.state.value.heldOption)
        }
        compose.onNodeWithTag("magic-square-$correct")
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, "Sorriso. Resposta correta"))
        compose.onNodeWithText("Seguinte").performScrollTo().assertIsDisplayed()
    }

    @Test fun nextIsReachableWithTextAtTwoHundredPercent() {
        val initial = FingerMagicSession()
        val solved = initial.reveal(initial.question.correctIndex)
        var advanced = false
        compose.setContent {
            val density = LocalDensity.current
            CompositionLocalProvider(LocalDensity provides Density(density.density, 2f)) {
                AprenderTheme {
                    FingerMagicScreen(FingerMagicUiState(session = solved), {}, {}, {}, {}, { advanced = true }, {}, {})
                }
            }
        }
        compose.onNodeWithText("Seguinte").performScrollTo().assertIsDisplayed().performClick()
        compose.runOnIdle { assertTrue(advanced) }
    }

    @Test fun completingTheDeckOffersRestart() {
        val last = FingerMagicSession(index = FingerMagicContent.questions.lastIndex)
        val completed = last.reveal(last.question.correctIndex).next()
        var restarted = false
        compose.setContent {
            AprenderTheme {
                FingerMagicScreen(FingerMagicUiState(session = completed), {}, {}, {}, {}, {}, { restarted = true }, {})
            }
        }
        compose.onNodeWithText("Parabéns! Descobriste os 111 sorrisos!").assertIsDisplayed()
        compose.onNodeWithText("Jogar novamente").performScrollTo().performClick()
        compose.runOnIdle { assertTrue(restarted) }
    }
}
