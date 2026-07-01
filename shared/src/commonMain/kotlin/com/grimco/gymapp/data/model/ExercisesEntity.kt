package com.grimco.gymapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exercises")
data class ExercisesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)
