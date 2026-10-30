package com.vcc.aprender.domain.repository

import com.vcc.aprender.domain.model.LearningTopic

interface LearningRepository {
    suspend fun getTopics(): List<LearningTopic>
}
