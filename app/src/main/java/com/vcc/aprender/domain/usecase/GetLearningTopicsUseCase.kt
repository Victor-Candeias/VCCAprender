package com.vcc.aprender.domain.usecase

import com.vcc.aprender.domain.model.LearningTopic
import com.vcc.aprender.domain.repository.LearningRepository

class GetLearningTopicsUseCase(private val repository: LearningRepository) {
    suspend operator fun invoke(): List<LearningTopic> = repository.getTopics()
}
