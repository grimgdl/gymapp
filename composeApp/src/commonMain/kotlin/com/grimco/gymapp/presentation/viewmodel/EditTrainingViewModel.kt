package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.dtos.TrainingExercise
import com.grimco.gymapp.data.model.ExercisesEntity
import com.grimco.gymapp.data.model.TrainingExercisesEntity
import com.grimco.gymapp.data.repository.TrainingRepository
import com.grimco.gymapp.presentation.utils.FlowEditableField
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@OptIn(FlowPreview::class)
class EditTrainingViewModel(
    id: Long,
    private val repository: TrainingRepository
) : ViewModel() {


    private val _image = MutableStateFlow<Any?>(null)
    val image: StateFlow<Any?> = _image
    val training: StateFlow<TrainingExercise?> = repository.getTrainingWithExercisesById(id)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )


    private val _exercises = MutableStateFlow<List<ExercisesEntity>>(emptyList())
    val exercises: StateFlow<List<ExercisesEntity>> = _exercises.asStateFlow()

    val disciplineEditor = FlowEditableField(
        source = training
            .filterNotNull()
            .map { it.training.discipline },
        scope = viewModelScope,
        onCommit = { newValue ->
            val current = training.value?.training ?: return@FlowEditableField
            repository.updateTraining(
                current.copy(discipline = newValue)
            )
        }
    )

    fun addExerciseTraining(idTraining: Long, exercisesEntity: ExercisesEntity) {
        viewModelScope.launch {
            repository.addExerciseTraining(idTraining, exercisesEntity)
        }
    }
    fun loadExercises() {
        viewModelScope.launch {
            repository.getExercises().collect { data ->
                _exercises.value = data
            }
        }
    }


    fun setImage(image: Any?) {
        viewModelScope.launch {
            _image.value = image
        }
    }

    fun deleteTrainingExercise(trainingExercisesEntity: TrainingExercisesEntity) {
        viewModelScope.launch {
            repository.deleteTrainingExercise(trainingExercisesEntity)
        }
    }

}



