package com.vcc.aprender.data.progress

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vcc.aprender.data.profile.ChildProfile

@Entity(
    tableName = "answer_history",
    foreignKeys = [ForeignKey(
        entity = ChildProfile::class, parentColumns = ["id"], childColumns = ["profileId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("profileId"), Index(value = ["profileId", "answeredAt"])]
)
data class AnswerHistory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val profileId: Long,
    val topicId: String,
    val correct: Boolean,
    val answeredAt: Long
)
