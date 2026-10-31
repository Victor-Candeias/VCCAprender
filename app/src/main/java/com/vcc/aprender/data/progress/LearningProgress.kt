package com.vcc.aprender.data.progress

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vcc.aprender.data.profile.ChildProfile

@Entity(
    tableName = "learning_progress",
    foreignKeys = [ForeignKey(
        entity = ChildProfile::class,
        parentColumns = ["id"],
        childColumns = ["profileId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("profileId"), Index(value = ["profileId", "topicId"], unique = true)]
)
data class LearningProgress(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val profileId: Long,
    val topicId: String,
    val completedActivities: Int = 0,
    val correctAnswers: Int = 0,
    val totalAnswers: Int = 0,
    val lastActivityAt: Long = 0L
)
