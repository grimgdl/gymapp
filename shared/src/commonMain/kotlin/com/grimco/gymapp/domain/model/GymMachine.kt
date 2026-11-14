package com.grimco.gymapp.domain.model

import com.grimco.gymapp.data.model.GymMachineEntity


data class GymMachine(
    val id: Long = 0,
    val name: String,
    val notes: String? = null,
    val imagePath: String? = null
)

fun GymMachine.toData() = GymMachineEntity(id, name, notes, imagePath)