package com.grimco.gymapp.data.dtos

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.grimco.gymapp.data.model.ExercisesEntity
import com.grimco.gymapp.data.model.TrainingEntity
import com.grimco.gymapp.data.model.TrainingExercisesEntity


data class TrainingExercise(
    @Embedded val training: TrainingEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = TrainingExercisesEntity::class,
            parentColumn = "idTraining",
            entityColumn = "idExercise"
        )
    )
    val exercises: List<ExercisesEntity>
)
