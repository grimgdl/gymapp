package com.grimco.gymapp.data.dtos

data class ExercisePrescription(
    val name: String,
    val sets: Int,
    val reps: Int,
    val machineName: String? = null
)
