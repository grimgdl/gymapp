package com.grimco.gymapp.data.dtos

import androidx.room.Embedded
import androidx.room.Relation
import com.grimco.gymapp.data.model.ExercisesEntity
import com.grimco.gymapp.data.model.GymMachineEntity

data class ExerciseWithMachine(
    @Embedded val exercise: ExercisesEntity,
    @Relation(
        parentColumn = "machineId",
        entityColumn = "id",
        entity = GymMachineEntity::class
    )
    val machine: GymMachineEntity?
)
