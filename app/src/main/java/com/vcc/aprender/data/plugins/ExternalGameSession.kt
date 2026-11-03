package com.vcc.aprender.data.plugins

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vcc.aprender.data.profile.ChildProfile

/** Owned by VCCAprender, never written directly by a plugin APK. */
@Entity(
    tableName = "external_game_sessions",
    foreignKeys = [ForeignKey(
        entity = ChildProfile::class,
        parentColumns = ["id"],
        childColumns = ["profileId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["profileId"]),
        Index(value = ["sessionId"], unique = true)
    ]
)
data class ExternalGameSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val profileId: Long,
    val sessionId: String,
    val gameId: String,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val completedAt: Long = System.currentTimeMillis()
)
