package com.grimco.gymapp.data.dtos

import androidx.room.Embedded
import androidx.room.Relation
import com.grimco.gymapp.data.model.ExercisesEntity
import com.grimco.gymapp.data.model.GymMachineEntity
import com.grimco.gymapp.data.model.TrainingExercisesEntity

data class ExerciseWithMachine(
    @Embedded val plan: TrainingExercisesEntity,
    @Relation(
        parentColumn = "idExercise",
        entityColumn = "id",
        entity = ExercisesEntity::class
    )
    val exercise: ExercisesEntity,
    @Relation(
        parentColumn = "machineId",
        entityColumn = "id",
        entity = GymMachineEntity::class
    )
    val machine: GymMachineEntity?
)
