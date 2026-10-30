package com.vcc.aprender.di

import com.vcc.aprender.data.repository.OfflineLearningRepository
import com.vcc.aprender.domain.repository.LearningRepository
import com.vcc.aprender.domain.usecase.GetLearningTopicsUseCase

class AppContainer {
    private val learningRepository: LearningRepository = OfflineLearningRepository()
    val getLearningTopics = GetLearningTopicsUseCase(learningRepository)
}
