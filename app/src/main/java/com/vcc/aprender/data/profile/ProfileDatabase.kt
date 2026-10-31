package com.vcc.aprender.data.profile

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChildProfile::class], version = 1, exportSchema = true)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}
