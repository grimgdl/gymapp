package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.repository.TrainingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailTrainingViewModel(
    repository: TrainingRepository
) : ViewModel() {

    private val _idTraining = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val training = _idTraining
        .filterNotNull()
        .flatMapLatest { id -> repository.getTrainingWithExercisesById(id) }
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000),
            null
        )
    fun updateIdTraining(id: Long) {
        viewModelScope.launch {
            _idTraining.value = id
        }
    }
}