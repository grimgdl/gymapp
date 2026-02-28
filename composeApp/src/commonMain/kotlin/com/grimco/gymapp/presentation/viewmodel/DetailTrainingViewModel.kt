package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.repository.TrainingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DetailTrainingViewModel(
    idTraining: Long,
    repository: TrainingRepository
) : ViewModel() {

    val training = repository.getTrainingWithExercisesById(idTraining)
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000),
            null
        )

}