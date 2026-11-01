package com.vcc.aprender.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import com.vcc.aprender.ui.profiles.ProfileScreen
import com.vcc.aprender.ui.profiles.ProfileViewModel
import com.vcc.aprender.ui.profiles.ProfileViewModelFactory
import com.vcc.aprender.ui.progress.ProgressScreen
import com.vcc.aprender.ui.progress.ProgressViewModel
import com.vcc.aprender.ui.progress.ProgressViewModelFactory
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vcc.aprender.di.AppContainer
import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.ui.cards.DeckSelectionScreen
import com.vcc.aprender.domain.cards.CardDifficulty
import com.vcc.aprender.domain.cards.DeckFilter
import com.vcc.aprender.ui.cards.DiscoverCardsScreen
import com.vcc.aprender.ui.cards.DiscoverCardsViewModel
import com.vcc.aprender.ui.cards.DiscoverCardsViewModelFactory
import com.vcc.aprender.ui.activities.ActivityScreen
import com.vcc.aprender.ui.activities.ActivityViewModel
import com.vcc.aprender.ui.activities.ActivityViewModelFactory
import com.vcc.aprender.ui.alphabet.AlphabetScreen
import com.vcc.aprender.ui.alphabet.AlphabetViewModel
import com.vcc.aprender.ui.alphabet.AlphabetViewModelFactory
import com.vcc.aprender.ui.parents.ParentalSettingsScreen
import com.vcc.aprender.ui.parents.ParentalSettingsViewModel
import com.vcc.aprender.ui.parents.ParentalSettingsViewModelFactory
import com.vcc.aprender.ui.home.HomeScreen
import com.vcc.aprender.ui.home.HomeViewModel
import com.vcc.aprender.ui.home.HomeViewModelFactory

private const val PROFILES = "profiles"
private const val HOME = "home"
private const val PROGRESS = "progress"
private const val PARENTS = "parents"
private const val TOPIC = "topic/{topicId}"
private const val ALPHABET = "alphabet"
private const val CARDS = "discover-cards"
private const val DECKS = "discover-decks"
private const val CARDS_FILTERED = "discover-cards/{topic}/{level}"

@Composable
fun AprenderNavHost(container: AppContainer) {
    val navController = rememberNavController()
    val profileModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(container.profileRepository))
    val profiles by profileModel.profiles.collectAsStateWithLifecycle()
    val selectedId by profileModel.selectedId.collectAsStateWithLifecycle()
    val error by profileModel.error.collectAsStateWithLifecycle()
    val progressModel: ProgressViewModel = viewModel(factory = ProgressViewModelFactory(container.progressRepository))
    val progress by progressModel.progress.collectAsStateWithLifecycle()
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

    NavHost(navController = navController, startDestination = PROFILES) {
        composable(PROFILES) {
            ProfileScreen(
                profiles = profiles, error = error,
                onClearError = profileModel::clearError,
                onSave = profileModel::save,
                onSelect = profileModel::select,
                onDelete = profileModel::delete
            )
        }
        composable(HOME) {
            if (selectedId == null || profiles.none { it.id == selectedId }) return@composable
            val model: HomeViewModel = viewModel(factory = HomeViewModelFactory(container.getLearningTopics))
            val state by model.uiState.collectAsStateWithLifecycle()
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                androidx.compose.material3.TextButton(onClick = { navController.navigate(PROFILES) }) {
                    Text("Mudar de perfil")
                }
                androidx.compose.material3.TextButton(onClick = { navController.navigate(PROGRESS) }) {
                    Text("Ver progresso")
                }
                androidx.compose.material3.TextButton(onClick = { navController.navigate(PARENTS) }) {
                    Text("Área parental e preferências")
                }
                androidx.compose.material3.TextButton(onClick = { navController.navigate(DECKS) }) {
                    Text("Descobre a Resposta A/B/C")
                }
                HomeScreen(
                    state = state,
                    onRetry = model::refresh,
                    onTopicClick = { topic -> navController.navigate(if (topic == LearningTopic.LETTERS) ALPHABET else "topic/${topic.name}") }
                )
            }
        }
        composable(DECKS) {
            DeckSelectionScreen(
                onStart = { filter ->
                    navController.navigate("discover-cards/${filter.topic?.name ?: "ALL"}/${filter.difficulty?.name ?: "ALL"}")
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = CARDS_FILTERED,
            arguments = listOf(
                navArgument("topic") { type = NavType.StringType },
                navArgument("level") { type = NavType.StringType }
            )
        ) { entry ->
            val topic = entry.arguments?.getString("topic")?.let { name ->
                LearningTopic.entries.firstOrNull { it.name == name }
            }
            val level = entry.arguments?.getString("level")?.let { name ->
                CardDifficulty.entries.firstOrNull { it.name == name }
            }
            val filter = DeckFilter(topic, level)
            val childId = selectedId
            if (childId != null && profiles.any { it.id == childId }) {
                val model: DiscoverCardsViewModel = viewModel(
                    key = "cards-$childId-${topic?.name ?: "ALL"}-${level?.name ?: "ALL"}",
                    factory = DiscoverCardsViewModelFactory(childId, container.progressRepository, filter)
                )
                val cardsState by model.state.collectAsStateWithLifecycle()
                DiscoverCardsScreen(
                    state = cardsState,
                    onChoose = model::choose,
                    onReveal = model::reveal,
                    onNext = model::next,
                    onRestart = model::restart,
                    onBack = { navController.popBackStack() }
                )
            }
        }
        composable(CARDS) {
            val childId = selectedId
            if (childId != null && profiles.any { it.id == childId }) {
                val model: DiscoverCardsViewModel = viewModel(
                    key = "cards-$childId",
                    factory = DiscoverCardsViewModelFactory(childId, container.progressRepository)
                )
                val cardsState by model.state.collectAsStateWithLifecycle()
                DiscoverCardsScreen(
                    state = cardsState,
                    onChoose = model::choose,
                    onReveal = model::reveal,
                    onNext = model::next,
                    onRestart = model::restart,
                    onBack = { navController.popBackStack() }
                )
            }
        }
        composable(ALPHABET) {
            val childId = selectedId
            if (childId != null && profiles.any { it.id == childId }) {
                val model: AlphabetViewModel = viewModel(
                    key = "alphabet-$childId",
                    factory = AlphabetViewModelFactory(container.progressRepository, childId)
                )
                val alphabetState by model.state.collectAsStateWithLifecycle()
                AlphabetScreen(
                    state = alphabetState,
                    onPrevious = model::previous,
                    onNext = model::next,
                    onAnswer = model::answer,
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
                onBack = { navController.popBackStack() }
            )
        }
        composable(PROGRESS) {
            if (selectedId != null) ProgressScreen(progress = progress, onBack = { navController.popBackStack() })
        }
        composable(
            route = TOPIC,
            arguments = listOf(navArgument("topicId") { type = NavType.StringType })
        ) { entry ->
            val topic = entry.arguments?.getString("topicId")?.let { id ->
                LearningTopic.entries.firstOrNull { it.name == id }
            }
            val childId = selectedId
            if (topic in listOf(LearningTopic.NUMBERS, LearningTopic.COLORS, LearningTopic.SHAPES, LearningTopic.ANIMALS, LearningTopic.SOUNDS, LearningTopic.WORDS)
                && childId != null && profiles.any { it.id == childId }) {
                val model: ActivityViewModel = viewModel(
                    key = "activity-${topic!!.name}-$childId",
                    factory = ActivityViewModelFactory(topic, childId, container.progressRepository)
                )
                val activityState by model.state.collectAsStateWithLifecycle()
                ActivityScreen(
                    state = activityState,
                    onAnswer = model::answer,
                    onPrevious = model::previous,
                    onNext = model::next,
                    onBack = { navController.popBackStack() }
                )
            } else {
                TopicScreen(topic = topic, onBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
private fun TopicScreen(topic: LearningTopic?, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(topic?.title ?: "Atividade não encontrada", style = MaterialTheme.typography.headlineLarge)
        Text(topic?.description ?: "Volta ao início e escolhe outra atividade.")
        if (topic != null) Text("As atividades estarão disponíveis em breve.")
        Button(onClick = onBack) { Text("Voltar ao início") }
    }
}
