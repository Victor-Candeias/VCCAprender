package com.vcc.aprender.ui.plugins

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.vcc.aprender.data.plugins.ExternalGameRepository
import com.vcc.aprender.plugins.InstalledGameRegistry
import com.vcc.aprender.pluginsdk.GameProtocol
import com.vcc.aprender.pluginsdk.GameRequest
import com.vcc.aprender.ui.home.HomeScreen
import kotlinx.coroutines.launch

/**
 * Host-owned launch/result gateway. Only primitives leave the host process.
 * No child name, age, avatar, DB handles, file paths or authentication secrets
 * are ever sent to a plugin.
 */
@Composable
fun PluginHomeScreen(
    profileId: Long,
    repository: ExternalGameRepository,
    onFingerMagicClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val context = LocalContext.current
    val owner = LocalLifecycleOwner.current
    val registry = remember(context) { InstalledGameRegistry(context.applicationContext) }
    var installed by remember(registry) { mutableStateOf(registry.installedGames()) }
    var message by rememberSaveable { mutableStateOf<String?>(null) }
    var pendingGameId by rememberSaveable { mutableStateOf<String?>(null) }
    var pendingSessionId by rememberSaveable { mutableStateOf<String?>(null) }
    var pendingComponent by rememberSaveable { mutableStateOf<String?>(null) }
    var pendingProfileId by rememberSaveable { mutableLongStateOf(-1L) }
    val scope = rememberCoroutineScope()

    // Recheck after users install or remove APKs in the Android installer.
    DisposableEffect(owner, registry) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) installed = registry.installedGames()
        }
        owner.lifecycle.addObserver(observer)
        onDispose { owner.lifecycle.removeObserver(observer) }
    }

    val launchGame = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        val gameId = pendingGameId
        val nonce = pendingSessionId
        val component = pendingComponent?.let(ComponentName::unflattenFromString)
        val originalProfileId = pendingProfileId

        // Clear saved pending launch even on Cancel or invalid return. A result
        // must never be processed more than once after activity recreation.
        pendingGameId = null
        pendingSessionId = null
        pendingComponent = null
        pendingProfileId = -1L

        if (gameId == null || nonce == null || component == null ||
            originalProfileId != profileId
        ) {
            message = "Sessão de jogo inválida ou expirada."
        } else if (activityResult.resultCode == Activity.RESULT_CANCELED) {
            message = "Jogo fechado sem guardar resultados."
        } else if (activityResult.resultCode != Activity.RESULT_OK ||
            registry.findTrusted(component, gameId) == null
        ) {
            message = "O jogo não passou na verificação de segurança."
        } else {
            val expected = GameRequest(gameId, nonce, originalProfileId)
            val result = GameProtocol.readResult(activityResult.data, expected)
            if (result == null) {
                message = "Resultado rejeitado: sessão ou formato inválido."
            } else {
                scope.launch {
                    try {
                        if (repository.record(result)) {
                            message = "Resultado guardado: ${result.correctAnswers} de ${result.totalQuestions} respostas certas."
                        } else {
                            message = "Esta sessão de jogo já tinha sido guardada."
                        }
                    } catch (_: Exception) {
                        message = "Não foi possível guardar o resultado do jogo."
                    }
                }
            }
        }
    }

    HomeScreen(
        onFingerMagicClick = onFingerMagicClick,
        onSettingsClick = onSettingsClick,
        installedGames = installed,
        onInstalledGameClick = { game ->
            val trusted = registry.findTrusted(game.component, game.gameId)
            if (trusted == null) {
                installed = registry.installedGames()
                message = "Jogo indisponível ou assinatura não reconhecida."
            } else {
                try {
                    val request = GameProtocol.newRequest(trusted.gameId, profileId)
                    pendingGameId = request.gameId
                    pendingSessionId = request.sessionId
                    pendingProfileId = request.profileId
                    pendingComponent = trusted.component.flattenToString()
                    message = null
                    launchGame.launch(GameProtocol.createLaunchIntent(trusted.component, request))
                } catch (_: ActivityNotFoundException) {
                    pendingGameId = null
                    pendingSessionId = null
                    pendingComponent = null
                    pendingProfileId = -1L
                    installed = registry.installedGames()
                    message = "O jogo deixou de estar instalado."
                } catch (_: SecurityException) {
                    pendingGameId = null
                    pendingSessionId = null
                    pendingComponent = null
                    pendingProfileId = -1L
                    message = "O Android bloqueou a abertura deste jogo."
                }
            }
        },
        pluginMessage = message
    )
}
