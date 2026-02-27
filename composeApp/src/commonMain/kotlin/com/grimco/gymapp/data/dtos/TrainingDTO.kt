package com.grimco.gymapp.data.dtos

data class TrainingDTO(
    val id: Long = 0,
    val discipline: String,
    val image: String,
    val timeOperation: Long = 0,
    val minutes: String = "",
    val intensity: String
)