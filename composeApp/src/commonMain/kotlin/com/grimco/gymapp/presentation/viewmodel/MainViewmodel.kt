package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.dao.TrainingDao
import com.grimco.gymapp.data.dtos.TrainingDTO
import com.grimco.gymapp.data.mapper.toDto
import com.grimco.gymapp.data.model.TrainingEntity
import com.grimco.gymapp.data.repository.TrainingRepository
import com.grimco.gymapp.domain.repository.SmartwatchManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewmodel(
    repository: TrainingRepository,
    private val smartwatchManager: SmartwatchManager
): ViewModel() {

    init {
        viewModelScope.launch {
            val steps = smartwatchManager.getSteps()
            println("Steps by gus $steps")
        }
    }

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