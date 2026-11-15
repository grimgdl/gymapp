package com.grimco.gymapp.data.dtos

import androidx.room.Embedded
import androidx.room.Relation
import com.grimco.gymapp.data.model.TrainingEntity
import com.grimco.gymapp.data.model.TrainingExercisesEntity

data class TrainingExercise(
    @Embedded val training: TrainingEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "idTraining",
        entity = TrainingExercisesEntity::class
    )
    val exercises: List<ExerciseWithMachine>
)
