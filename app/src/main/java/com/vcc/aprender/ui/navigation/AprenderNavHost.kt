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
import com.vcc.aprender.ui.alphabet.AlphabetScreen
import com.vcc.aprender.ui.alphabet.AlphabetViewModel
import com.vcc.aprender.ui.alphabet.AlphabetViewModelFactory
import com.vcc.aprender.ui.home.HomeScreen
import com.vcc.aprender.ui.home.HomeViewModel
import com.vcc.aprender.ui.home.HomeViewModelFactory

private const val PROFILES = "profiles"
private const val HOME = "home"
private const val PROGRESS = "progress"
private const val TOPIC = "topic/{topicId}"
private const val ALPHABET = "alphabet"

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
            androidx.compose.material3.TextButton(onClick = { navController.navigate(PROFILES) }) {
                Text("Mudar de perfil")
            }
            androidx.compose.material3.TextButton(onClick = { navController.navigate(PROGRESS) }) { Text("Ver progresso") }
            HomeScreen(
                state = state,
                onRetry = model::refresh,
                onTopicClick = { topic -> navController.navigate(if (topic == LearningTopic.LETTERS) ALPHABET else "topic/${topic.name}") }
            )
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
            TopicScreen(topic = topic, onBack = { navController.popBackStack() })
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
