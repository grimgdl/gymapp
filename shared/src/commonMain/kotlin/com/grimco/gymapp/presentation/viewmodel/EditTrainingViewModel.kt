package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.dtos.TrainingExercise
import com.grimco.gymapp.data.model.ExercisesEntity
import com.grimco.gymapp.data.model.TrainingExercisesEntity
import com.grimco.gymapp.data.repository.TrainingRepository
import com.grimco.gymapp.domain.repository.ImageStorage
import com.grimco.gymapp.presentation.utils.FlowEditableField
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@OptIn(FlowPreview::class)
class EditTrainingViewModel(
    private val repository: TrainingRepository,
    private val imageStorage: ImageStorage
) : ViewModel() {
    private val _idTraining = MutableStateFlow<Long?>(null)
    @OptIn(ExperimentalCoroutinesApi::class)
    val training: StateFlow<TrainingExercise?> = _idTraining
        .filterNotNull()
        .flatMapLatest { id -> repository.getTrainingWithExercisesById(id) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    val exercises: StateFlow<List<ExercisesEntity>> = repository.getExercises()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

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

    fun setIdTraining(idTraining: Long) {
        viewModelScope.launch {
            _idTraining.value = idTraining
        }
    }

    fun addExerciseTraining(idTraining: Long, exercisesEntity: ExercisesEntity) {
        viewModelScope.launch {
            repository.addExerciseTraining(idTraining, exercisesEntity)
        }
    }
    fun insertExercise(name: String) {
        viewModelScope.launch {
            repository.insertExercise(
                ExercisesEntity(
                    name = name
                )
            )
        }
    }
    fun saveImage(idTraining: Long, image: ByteArray) {
        viewModelScope.launch {
            val path = imageStorage.saveImage("test", image)
            path?.let {
                repository.updateImage(idTraining, path)
            }
        }
    }


    fun deleteTrainingExercise(trainingExercisesEntity: TrainingExercisesEntity) {
        viewModelScope.launch {
            repository.deleteTrainingExercise(trainingExercisesEntity)
        }
    }

}



