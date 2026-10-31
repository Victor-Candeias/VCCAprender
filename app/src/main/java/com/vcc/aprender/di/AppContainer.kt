package com.vcc.aprender.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vcc.aprender.data.settings.ParentalSettingsRepository
import com.vcc.aprender.data.profile.ProfileDatabase
import com.vcc.aprender.data.profile.ProfileRepository
import com.vcc.aprender.data.progress.ProgressRepository
import com.vcc.aprender.data.repository.OfflineLearningRepository
import com.vcc.aprender.domain.repository.LearningRepository
import com.vcc.aprender.domain.usecase.GetLearningTopicsUseCase

private val Context.profileDataStore by preferencesDataStore(name = "profiles")

private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""CREATE TABLE IF NOT EXISTS learning_progress (
            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            profileId INTEGER NOT NULL,
            topicId TEXT NOT NULL,
            completedActivities INTEGER NOT NULL,
            correctAnswers INTEGER NOT NULL,
            totalAnswers INTEGER NOT NULL,
            lastActivityAt INTEGER NOT NULL,
            FOREIGN KEY(profileId) REFERENCES child_profiles(id) ON DELETE CASCADE
        )""")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_learning_progress_profileId ON learning_progress(profileId)")
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_learning_progress_profileId_topicId ON learning_progress(profileId, topicId)")
    }
}

class AppContainer(context: Context) {
    private val learningRepository: LearningRepository = OfflineLearningRepository()
    val getLearningTopics = GetLearningTopicsUseCase(learningRepository)
    val parentalSettingsRepository = ParentalSettingsRepository(context)
    private val database = Room.databaseBuilder(
        context.applicationContext, ProfileDatabase::class.java, "vcc_profiles.db"
    ).addMigrations(MIGRATION_1_2).build()
    val profileRepository = ProfileRepository(database.profileDao(), context.applicationContext.profileDataStore)
    val progressRepository = ProgressRepository(database)
}
