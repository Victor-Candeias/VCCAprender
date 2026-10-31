package com.vcc.aprender.data.progress

import androidx.room.withTransaction
import com.vcc.aprender.data.profile.ProfileDatabase
import com.vcc.aprender.domain.model.LearningTopic
import kotlinx.coroutines.flow.Flow

class ProgressRepository(private val database: ProfileDatabase) {
    private val dao = database.progressDao()

    fun observe(profileId: Long): Flow<List<LearningProgress>> {
        require(profileId > 0) { "Perfil inválido." }
        return dao.observeByProfile(profileId)
    }

    suspend fun recordAnswer(profileId: Long, topic: LearningTopic, correct: Boolean, now: Long = System.currentTimeMillis()) {
        require(profileId > 0) { "Perfil inválido." }
        database.withTransaction {
            val old = dao.find(profileId, topic.name)
            val current = old ?: LearningProgress(profileId = profileId, topicId = topic.name)
            dao.upsert(current.copy(
                correctAnswers = current.correctAnswers + if (correct) 1 else 0,
                totalAnswers = current.totalAnswers + 1,
                lastActivityAt = now
            ))
        }
    }

    suspend fun completeActivity(profileId: Long, topic: LearningTopic, now: Long = System.currentTimeMillis()) {
        require(profileId > 0) { "Perfil inválido." }
        database.withTransaction {
            val old = dao.find(profileId, topic.name)
            val current = old ?: LearningProgress(profileId = profileId, topicId = topic.name)
            dao.upsert(current.copy(
                completedActivities = current.completedActivities + 1,
                lastActivityAt = now
            ))
        }
    }
}
