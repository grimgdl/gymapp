package com.grimco.gymapp.data.dtos

data class TrainingDTO(
    val id: Long = 0,
    val discipline: String,
    val image: String,
    val minutes: String,
    val intensity: String
)