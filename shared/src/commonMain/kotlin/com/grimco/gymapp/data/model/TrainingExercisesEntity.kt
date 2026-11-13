package com.grimco.gymapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "training_exercises",
    primaryKeys = ["idTraining", "idExercise"]
)
data class TrainingExercisesEntity(
    val idTraining: Long,
    val idExercise: Long,
    @ColumnInfo(defaultValue = "3") val plannedSets: Int = 3,
    @ColumnInfo(defaultValue = "10") val plannedReps: Int = 10
)
