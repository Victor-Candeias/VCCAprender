package com.vcc.aprender.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vcc.aprender.data.cards.CustomCardRepository
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

private val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""CREATE TABLE IF NOT EXISTS custom_cards (
            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            topic TEXT NOT NULL,
            difficulty TEXT NOT NULL,
            question TEXT NOT NULL,
            firstOption TEXT NOT NULL,
            secondOption TEXT NOT NULL,
            thirdOption TEXT NOT NULL,
            correctIndex INTEGER NOT NULL
        )""")
    }
}

private val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""CREATE TABLE IF NOT EXISTS answer_history (
            id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            profileId INTEGER NOT NULL,
            topicId TEXT NOT NULL,
            correct INTEGER NOT NULL,
            answeredAt INTEGER NOT NULL,
            FOREIGN KEY(profileId) REFERENCES child_profiles(id) ON DELETE CASCADE
        )""")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_answer_history_profileId ON answer_history(profileId)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_answer_history_profileId_answeredAt ON answer_history(profileId, answeredAt)")
    }
}

class AppContainer(context: Context) {
    private val learningRepository: LearningRepository = OfflineLearningRepository()
    val getLearningTopics = GetLearningTopicsUseCase(learningRepository)
    val parentalSettingsRepository = ParentalSettingsRepository(context)
    private val database = Room.databaseBuilder(
        context.applicationContext, ProfileDatabase::class.java, "vcc_profiles.db"
    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4).build()
    val profileRepository = ProfileRepository(database.profileDao(), context.applicationContext.profileDataStore)
    val progressRepository = ProgressRepository(database)
    val customCardRepository = CustomCardRepository(database.customCardDao())
}
