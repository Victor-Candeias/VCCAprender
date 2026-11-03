package com.vcc.aprender.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vcc.aprender.data.settings.ParentalSettings
import com.vcc.aprender.di.AppContainer
import com.vcc.aprender.ui.components.GameMotionProvider
import com.vcc.aprender.ui.plugins.PluginHomeScreen
import com.vcc.aprender.ui.home.SettingsScreen
import com.vcc.aprender.ui.magic.FingerMagicScreen
import com.vcc.aprender.ui.magic.FingerMagicViewModel
import com.vcc.aprender.ui.magic.FingerMagicViewModelFactory
import com.vcc.aprender.ui.parents.ParentalSettingsScreen
import com.vcc.aprender.ui.parents.ParentalSettingsViewModel
import com.vcc.aprender.ui.parents.ParentalSettingsViewModelFactory
import com.vcc.aprender.ui.profiles.ProfileScreen
import com.vcc.aprender.ui.profiles.ProfileViewModel
import com.vcc.aprender.ui.profiles.ProfileViewModelFactory
import com.vcc.aprender.ui.progress.ProgressScreen
import com.vcc.aprender.ui.progress.ProgressViewModel
import com.vcc.aprender.ui.progress.ProgressViewModelFactory

// VA-034: only the Finger Magic game is a playable destination.
// Legacy alphabet, learning topics, cards, reading and editor routes are removed.
private const val PROFILES = "profiles"
private const val HOME = "home"
private const val SETTINGS = "settings"
private const val PROGRESS = "progress"
private const val PARENTS = "parents"
private const val FINGER_MAGIC = "finger-magic"

@Composable
fun AprenderNavHost(container: AppContainer) {
    val navController = rememberNavController()
    val motionSettings by container.parentalSettingsRepository.settings.collectAsStateWithLifecycle(
        initialValue = ParentalSettings(animationsEnabled = false)
    )
    val profileModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(container.profileRepository)
    )
    val profiles by profileModel.profiles.collectAsStateWithLifecycle()
    val selectedId by profileModel.selectedId.collectAsStateWithLifecycle()
    val profileError by profileModel.error.collectAsStateWithLifecycle()
    val progressModel: ProgressViewModel = viewModel(
        factory = ProgressViewModelFactory(container.progressRepository, container.externalGameRepository)
    )
    val progress by progressModel.progress.collectAsStateWithLifecycle()
    val answerHistory by progressModel.history.collectAsStateWithLifecycle()
    val externalSessions by progressModel.externalSessions.collectAsStateWithLifecycle()

    LaunchedEffect(selectedId) { progressModel.selectProfile(selectedId) }
    LaunchedEffect(selectedId, profiles) {
        if (selectedId != null && profiles.any { it.id == selectedId }) {
            navController.navigate(HOME) {
                popUpTo(PROFILES) { inclusive = false }
                launchSingleTop = true
            }
        } else if (selectedId == null && navController.currentDestination?.route != PROFILES) {
            navController.navigate(PROFILES) {
                popUpTo(PROFILES) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    GameMotionProvider(parentalEnabled = motionSettings.animationsEnabled) {
        NavHost(navController = navController, startDestination = PROFILES) {
            composable(PROFILES) {
                ProfileScreen(
                    profiles = profiles,
                    error = profileError,
                    onClearError = profileModel::clearError,
                    onSave = profileModel::save,
                    onSelect = profileModel::select,
                    onDelete = profileModel::delete,
                    onDeleteAll = profileModel::deleteAll
                )
            }
            composable(HOME) {
                if (selectedId == null || profiles.none { it.id == selectedId }) return@composable
                PluginHomeScreen(
                    profileId = selectedId!!,
                    repository = container.externalGameRepository,
                    onFingerMagicClick = { navController.navigate(FINGER_MAGIC) },
                    onSettingsClick = { navController.navigate(SETTINGS) }
                )
            }
            composable(SETTINGS) {
                SettingsScreen(
                    onProfiles = { navController.navigate(PROFILES) },
                    onProgress = { navController.navigate(PROGRESS) },
                    onParents = { navController.navigate(PARENTS) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(FINGER_MAGIC) {
                val childId = selectedId
                if (childId != null && profiles.any { it.id == childId }) {
                    val model: FingerMagicViewModel = viewModel(
                        key = "finger-magic-$childId",
                        factory = FingerMagicViewModelFactory(childId, container.progressRepository)
                    )
                    val game by model.state.collectAsStateWithLifecycle()
                    FingerMagicScreen(
                        state = game,
                        onPress = { model.press(it) },
                        onRelease = model::releaseTouch,
                        onAccessibleReveal = { model.press(it, accessible = true) },
                        onCancelTouch = model::cancelTouch,
                        onNext = model::next,
                        onRestart = model::restart,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
            composable(PARENTS) {
                val model: ParentalSettingsViewModel = viewModel(
                    factory = ParentalSettingsViewModelFactory(container.parentalSettingsRepository)
                )
                val settings by model.settings.collectAsStateWithLifecycle()
                ParentalSettingsScreen(
                    settings = settings,
                    onAudioChanged = model::setAudioEnabled,
                    onSpeechRateChanged = model::setSpeechRate,
                    onAnimationsChanged = model::setAnimationsEnabled,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(PROGRESS) {
                if (selectedId != null) {
                    ProgressScreen(
                        progress = progress,
                        history = answerHistory,
                        externalSessions = externalSessions,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
