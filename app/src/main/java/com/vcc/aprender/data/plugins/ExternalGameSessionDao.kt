package com.vcc.aprender.data.plugins

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExternalGameSessionDao {
    // Identical session IDs may not be inserted twice after Activity recreation.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOnce(session: ExternalGameSession): Long

    @Query("SELECT * FROM external_game_sessions WHERE profileId = :profileId ORDER BY completedAt DESC, id DESC")
    fun observeByProfile(profileId: Long): Flow<List<ExternalGameSession>>
}
