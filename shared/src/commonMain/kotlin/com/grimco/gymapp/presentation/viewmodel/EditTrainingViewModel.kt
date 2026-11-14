package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.dtos.TrainingExercise
import com.grimco.gymapp.data.model.ExercisesEntity
import com.grimco.gymapp.data.model.TrainingExercisesEntity
import com.grimco.gymapp.data.model.toDomain
import com.grimco.gymapp.data.repository.TrainingRepository
import com.grimco.gymapp.domain.model.GymMachine
import com.grimco.gymapp.domain.model.toData
import com.grimco.gymapp.domain.repository.ImageStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class EditTrainingViewModel(
    private val repository: TrainingRepository,
    private val imageStorage: ImageStorage
) : ViewModel() {
    private val _idTraining = MutableStateFlow<Long?>(null)
    private var isInitialized = false
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

    val machines: StateFlow<List<GymMachine>> = repository.observeMachines()
        .map { entities ->
            entities.map { it.toDomain() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )



    private val _disciplineInput = MutableStateFlow("")
    val disciplineInput: StateFlow<String> = _disciplineInput.asStateFlow()


    init {

        viewModelScope.launch {
            training.filterNotNull().collect { trainingExercise ->
                val dbDiscipline = trainingExercise.training.discipline
                // Solo inicializamos si no se ha hecho, o si cambió de entrenamiento (opcional)
                if (!isInitialized && dbDiscipline.isNotEmpty()) {
                    _disciplineInput.value = dbDiscipline
                    isInitialized = true
                }
            }
        }

        viewModelScope.launch {
            _disciplineInput
                .debounce(500L)
                .distinctUntilChanged()
                .collect { newDiscipline ->
                    val currentTrainingExercise = training.value?.training ?: return@collect
                    if (currentTrainingExercise.discipline != newDiscipline && newDiscipline.isNotBlank()) {
                        repository.updateTraining(
                            currentTrainingExercise.copy(discipline = newDiscipline)
                        )
                    }
                }


        }

    }


    fun onDisciplineChanged(newText: String) {
        _disciplineInput.value = newText
    }

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
            repository.insertExercise(ExercisesEntity(name = name))
        }
    }

    fun assignMachine(exerciseId: Long, machineId: Long?) {
        viewModelScope.launch {
            repository.assignMachine(exerciseId, machineId)
        }
    }

    fun saveMachine(
        existing: GymMachine?,
        name: String,
        notes: String,
        imageBytes: ByteArray?
    ) {
        viewModelScope.launch {
            val imagePath = imageBytes?.let {
                imageStorage.saveImage("machine_${existing?.id ?: name}", it)
            } ?: existing?.imagePath
            val machine = GymMachine(
                id = existing?.id ?: 0,
                name = name.trim(),
                notes = notes.trim().takeIf(String::isNotEmpty),
                imagePath = imagePath
            )
            if (existing == null) {
                repository.createMachine(machine.toData())
            } else {
                repository.updateMachine(machine.toData())
            }
        }
    }

    fun deleteMachine(machine: GymMachine) {
        viewModelScope.launch {
            repository.deleteMachine(machine.toData())
        }
    }

    fun updateExercisePlan(
        idTraining: Long,
        idExercise: Long,
        plannedSets: Int,
        plannedReps: Int
    ) {
        viewModelScope.launch {
            repository.updateExercisePlan(
                TrainingExercisesEntity(
                    idTraining = idTraining,
                    idExercise = idExercise,
                    plannedSets = plannedSets.coerceAtLeast(1),
                    plannedReps = plannedReps.coerceAtLeast(1)
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



