package com.grimco.gymapp.data.mapper

import com.grimco.gymapp.data.dtos.TrainingDTO
import com.grimco.gymapp.data.model.TrainingEntity


fun TrainingEntity.toDto() = TrainingDTO(
    id = this.id,
    discipline = this.discipline,
    image = this.image,
    timeOperation = this.timeOperation,
    minutes = "24 min",
    intensity = this.intensity
)