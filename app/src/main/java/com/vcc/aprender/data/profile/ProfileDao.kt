package com.vcc.aprender.data.profile

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM child_profiles ORDER BY name COLLATE NOCASE")
    fun observeAll(): Flow<List<ChildProfile>>

    @Query("SELECT * FROM child_profiles WHERE id = :id LIMIT 1")
    suspend fun find(id: Long): ChildProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(profile: ChildProfile): Long

    @Delete
    suspend fun delete(profile: ChildProfile)
}
