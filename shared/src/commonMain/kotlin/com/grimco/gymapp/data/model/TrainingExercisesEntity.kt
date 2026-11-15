package com.grimco.gymapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "training_exercises",
    primaryKeys = ["idTraining", "idExercise"],
    foreignKeys = [
        ForeignKey(
            entity = GymMachineEntity::class,
            parentColumns = ["id"],
            childColumns = ["machineId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("machineId")]
)
data class TrainingExercisesEntity(
    val idTraining: Long,
    val idExercise: Long,
    @ColumnInfo(defaultValue = "3") val plannedSets: Int = 3,
    @ColumnInfo(defaultValue = "10") val plannedReps: Int = 10,
    val machineId: Long? = null
)
