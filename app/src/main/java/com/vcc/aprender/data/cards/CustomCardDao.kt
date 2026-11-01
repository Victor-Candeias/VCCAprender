package com.vcc.aprender.data.cards

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomCardDao {
    @Query("SELECT * FROM custom_cards ORDER BY id DESC")
    fun observeAll(): Flow<List<CustomCardEntity>>

    @Query("SELECT * FROM custom_cards ORDER BY id DESC")
    suspend fun getAll(): List<CustomCardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(card: CustomCardEntity): Long

    @Query("DELETE FROM custom_cards WHERE id = :id")
    suspend fun delete(id: Long)
}
