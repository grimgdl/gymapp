package com.grimco.gymapp.data.model

import androidx.room.Entity


@Entity(
    tableName = "training_exercises",
    primaryKeys = ["idTraining", "idExercise"]
)
data class TrainingExercisesEntity(
    val idTraining: Long,
    val idExercise: Long
)
