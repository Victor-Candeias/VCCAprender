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
import com.vcc.aprender.ui.home.HomeScreen
import com.vcc.aprender.ui.home.HomeViewModel
import com.vcc.aprender.ui.home.HomeViewModelFactory

private const val HOME = "home"
private const val TOPIC = "topic/{topicId}"

@Composable
fun AprenderNavHost(container: AppContainer) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME) {
        composable(HOME) {
            val model: HomeViewModel = viewModel(factory = HomeViewModelFactory(container.getLearningTopics))
            val state by model.uiState.collectAsStateWithLifecycle()
            HomeScreen(
                state = state,
                onRetry = model::refresh,
                onTopicClick = { topic -> navController.navigate("topic/${topic.name}") }
            )
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
