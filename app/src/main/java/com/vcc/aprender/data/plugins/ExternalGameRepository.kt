package com.vcc.aprender.data.plugins

import com.vcc.aprender.data.profile.ProfileDatabase
import com.vcc.aprender.pluginsdk.GameResult
import com.vcc.aprender.pluginsdk.GameValidation
import kotlinx.coroutines.flow.Flow

/**
 * Host-only storage. The plugin never gets database access.
 * The caller must also re-verify package signature and launch session before calling.
 */
class ExternalGameRepository(database: ProfileDatabase) {
    private val dao = database.externalGameSessionDao()

    fun observeByProfile(profileId: Long): Flow<List<ExternalGameSession>> {
        require(profileId > 0L)
        return dao.observeByProfile(profileId)
    }

    suspend fun record(result: GameResult): Boolean {
        val req = result.request
        require(GameValidation.validResult(
            req, req.gameId, req.sessionId, req.profileId,
            com.vcc.aprender.pluginsdk.GameContract.PROTOCOL_VERSION,
            result.correctAnswers, result.totalQuestions
        ))
        return dao.insertOnce(
            ExternalGameSession(
                profileId = req.profileId,
                sessionId = req.sessionId,
                gameId = req.gameId,
                correctAnswers = result.correctAnswers,
                totalQuestions = result.totalQuestions
            )
        ) != -1L
    }
}
