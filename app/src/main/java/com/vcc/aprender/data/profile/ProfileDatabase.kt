package com.vcc.aprender.data.profile

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vcc.aprender.data.cards.CustomCardEntity
import com.vcc.aprender.data.cards.CustomCardDao
import com.vcc.aprender.data.progress.AnswerHistory
import com.vcc.aprender.data.progress.AnswerHistoryDao
import com.vcc.aprender.data.progress.LearningProgress
import com.vcc.aprender.data.progress.ProgressDao
import com.vcc.aprender.data.plugins.ExternalGameSession
import com.vcc.aprender.data.plugins.ExternalGameSessionDao

@Database(entities = [ChildProfile::class, LearningProgress::class, CustomCardEntity::class, AnswerHistory::class, ExternalGameSession::class], version = 5, exportSchema = false)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun progressDao(): ProgressDao
    abstract fun customCardDao(): CustomCardDao
    abstract fun answerHistoryDao(): AnswerHistoryDao
    abstract fun externalGameSessionDao(): ExternalGameSessionDao
}
