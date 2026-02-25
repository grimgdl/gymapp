package com.grimco.gymapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "trainings")
data class TrainingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val discipline: String,
    val image: String,
    val timeOperation: Long,
    val intensity: String
)