package com.grimco.gymapp.data.dtos

import androidx.room.Embedded
import androidx.room.Relation
import com.grimco.gymapp.data.model.SessionExerciseEntity
import com.grimco.gymapp.data.model.WorkoutSessionEntity
import com.grimco.gymapp.data.model.WorkoutSetEntity

data class SessionExerciseWithSets(
    @Embedded val exercise: SessionExerciseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "sessionExerciseId",
        entity = WorkoutSetEntity::class
    )
    val sets: List<WorkoutSetEntity>
)

data class WorkoutSessionWithExercises(
    @Embedded val session: WorkoutSessionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId",
        entity = SessionExerciseEntity::class
    )
    val exercises: List<SessionExerciseWithSets>
)
