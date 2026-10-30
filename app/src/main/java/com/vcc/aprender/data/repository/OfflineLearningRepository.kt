package com.vcc.aprender.data.repository

import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.domain.repository.LearningRepository

class OfflineLearningRepository : LearningRepository {
    override suspend fun getTopics(): List<LearningTopic> = LearningTopic.entries
}
