package com.vcc.aprender.data.progress

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswerHistoryDao {
    @Insert
    suspend fun insert(answer: AnswerHistory)

    @Query("SELECT * FROM answer_history WHERE profileId = :profileId ORDER BY answeredAt DESC, id DESC")
    fun observeByProfile(profileId: Long): Flow<List<AnswerHistory>>

    @Query("SELECT * FROM answer_history WHERE profileId = :profileId ORDER BY answeredAt DESC, id DESC LIMIT :limit")
    suspend fun recent(profileId: Long, limit: Int): List<AnswerHistory>
}
