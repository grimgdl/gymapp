package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.dtos.TrainingExercise
import com.grimco.gymapp.data.repository.TrainingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class EditTrainingViewModel(
    id: Long,
    repository: TrainingRepository
): ViewModel() {

    val training: StateFlow<TrainingExercise?> = repository.getTrainingWithExercisesById(id)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )
}