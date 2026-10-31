package com.vcc.aprender.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.vcc.aprender.data.profile.ProfileDatabase
import com.vcc.aprender.data.profile.ProfileRepository
import com.vcc.aprender.data.repository.OfflineLearningRepository
import com.vcc.aprender.domain.repository.LearningRepository
import com.vcc.aprender.domain.usecase.GetLearningTopicsUseCase

private val Context.profileDataStore by preferencesDataStore(name = "profiles")

class AppContainer(context: Context) {
    private val learningRepository: LearningRepository = OfflineLearningRepository()
    val getLearningTopics = GetLearningTopicsUseCase(learningRepository)
    private val database = Room.databaseBuilder(
        context.applicationContext, ProfileDatabase::class.java, "vcc_profiles.db"
    ).build()
    val profileRepository = ProfileRepository(database.profileDao(), context.applicationContext.profileDataStore)
}
