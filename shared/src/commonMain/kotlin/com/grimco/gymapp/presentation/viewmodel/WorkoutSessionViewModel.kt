package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.dtos.ExercisePrescription
import com.grimco.gymapp.data.dtos.WorkoutSessionWithExercises
import com.grimco.gymapp.data.model.WorkoutSessionEntity
import com.grimco.gymapp.data.model.WorkoutSetEntity
import com.grimco.gymapp.data.repository.TrainingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Clock

data class WorkoutSessionUiState(
    val loading: Boolean = true,
    val draftToResolve: WorkoutSessionEntity? = null,
    val session: WorkoutSessionWithExercises? = null,
    val error: String? = null
)

class WorkoutSessionViewModel(private val repository: TrainingRepository) : ViewModel() {
    private val sessionId = MutableStateFlow<Long?>(null)
    private val _uiState = MutableStateFlow(WorkoutSessionUiState())
    val uiState: StateFlow<WorkoutSessionUiState> = _uiState

    @OptIn(ExperimentalCoroutinesApi::class)
    val session = sessionId.filterNotNull()
        .flatMapLatest(repository::observeSession)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun prepare(routineId: Long) {
        if (!_uiState.value.loading) return
        viewModelScope.launch {
            val draft = repository.observeDraft().first()
            if (draft != null) {
                _uiState.value = WorkoutSessionUiState(loading = false, draftToResolve = draft)
                return@launch
            }
            startNewSession(routineId)
        }
    }

    fun resumeDraft() {
        val draft = _uiState.value.draftToResolve ?: return
        _uiState.value = WorkoutSessionUiState(loading = false)
        sessionId.value = draft.id
    }

    fun discardDraftAndStart(routineId: Long) {
        val draft = _uiState.value.draftToResolve ?: return
        _uiState.value = WorkoutSessionUiState(loading = true)
        viewModelScope.launch {
            repository.deleteSession(draft.id)
            startNewSession(routineId)
        }
    }

    private suspend fun startNewSession(routineId: Long) {
        val routine = repository.getTrainingWithExercisesById(routineId).first()
        if (routine == null) {
            _uiState.value = WorkoutSessionUiState(loading = false, error = "No se encontró la rutina.")
            return
        }
        val id = repository.createSession(
            routineName = routine.training.discipline,
            exercisePlans = routine.exercises.map { exercise ->
                val plan = routine.exercisePlans.firstOrNull { it.idExercise == exercise.id }
                ExercisePrescription(
                    name = exercise.name,
                    sets = plan?.plannedSets ?: 3,
                    reps = plan?.plannedReps ?: 10
                )
            },
            startedAt = Clock.System.now().toEpochMilliseconds()
        )
        _uiState.value = WorkoutSessionUiState(loading = false)
        sessionId.value = id
    }

    fun updateSet(set: WorkoutSetEntity) {
        viewModelScope.launch { repository.updateSet(set) }
    }

    fun finishSession(onFinished: () -> Unit) {
        val id = sessionId.value ?: return
        viewModelScope.launch {
            repository.finishSession(id, Clock.System.now().toEpochMilliseconds())
            sessionId.value = null
            onFinished()
        }
    }
}
