package com.vcc.aprender.data.profile

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vcc.aprender.data.cards.CustomCardEntity
import com.vcc.aprender.data.cards.CustomCardDao
import com.vcc.aprender.data.progress.LearningProgress
import com.vcc.aprender.data.progress.ProgressDao

@Database(entities = [ChildProfile::class, LearningProgress::class, CustomCardEntity::class], version = 3, exportSchema = false)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun progressDao(): ProgressDao
    abstract fun customCardDao(): CustomCardDao
}
