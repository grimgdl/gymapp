package com.grimco.gymapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.grimco.gymapp.domain.model.GymMachine

@Entity(tableName = "gym_machines")
data class GymMachineEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val notes: String? = null,
    val imagePath: String? = null
)


fun GymMachineEntity.toDomain() = GymMachine(
    id = id,
    name = name,
    notes = notes,
    imagePath = imagePath
)
