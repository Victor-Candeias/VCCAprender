package com.vcc.aprender.data.cards

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_cards")
data class CustomCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val topic: String,
    val difficulty: String,
    val question: String,
    val firstOption: String,
    val secondOption: String,
    val thirdOption: String,
    val correctIndex: Int
)
