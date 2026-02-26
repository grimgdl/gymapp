package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.dtos.TrainingDTO
import com.grimco.gymapp.data.dtos.TrainingExercise
import com.grimco.gymapp.data.model.TrainingEntity
import com.grimco.gymapp.data.repository.TrainingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class EditTrainingViewModel(
    private val id: Long,
    private val repository: TrainingRepository
): ViewModel() {

    val training: StateFlow<TrainingExercise?> = repository.getTrainingWithExercisesById(id)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )


    fun updateTraining(trainingEntity: TrainingEntity) {
        viewModelScope.launch {
            repository.updateTraining(trainingEntity)
        }
    }
}