package com.vcc.aprender.data.progress

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM learning_progress WHERE profileId = :profileId ORDER BY topicId")
    fun observeByProfile(profileId: Long): Flow<List<LearningProgress>>

    @Query("SELECT * FROM learning_progress WHERE profileId = :profileId AND topicId = :topicId LIMIT 1")
    suspend fun find(profileId: Long, topicId: String): LearningProgress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(progress: LearningProgress)

    @Query("DELETE FROM learning_progress WHERE profileId = :profileId")
    suspend fun deleteForProfile(profileId: Long)
}
