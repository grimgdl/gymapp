package com.grimco.gymapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_sessions")
data class WorkoutSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val routineName: String,
    val startedAt: Long,
    val endedAt: Long? = null,
    val status: String = "DRAFT"
)
