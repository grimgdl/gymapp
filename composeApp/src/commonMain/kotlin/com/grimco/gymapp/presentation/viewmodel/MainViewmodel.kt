package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.dao.TrainingDao
import com.grimco.gymapp.data.dtos.TrainingDTO
import com.grimco.gymapp.data.mapper.toDto
import com.grimco.gymapp.data.model.TrainingEntity
import com.grimco.gymapp.data.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewmodel(
    repository: TrainingRepository
): ViewModel() {

    val trainings: StateFlow<List<TrainingDTO>> = repository.getTrainingList()
        .map { entities ->
            entities.map { it.toDto() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}