package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.dtos.WorkoutSessionWithExercises
import com.grimco.gymapp.data.repository.TrainingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class WorkoutHistoryViewModel(repository: TrainingRepository) : ViewModel() {
    val sessions: StateFlow<List<WorkoutSessionWithExercises>> = repository.observeCompletedSessions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}
