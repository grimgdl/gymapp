package com.grimco.gymapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grimco.gymapp.data.repository.TrainingRepository
import com.grimco.gymapp.presentation.utils.convertToTime
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Clock

class DetailTrainingViewModel(
    repository: TrainingRepository
) : ViewModel() {

    private val _timer = MutableStateFlow<Long>(0)
    val timer: StateFlow<String> = _timer
        .map { it.convertToTime() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = "00:00"
        )

    private var timerJob: Job? = null
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

    fun startTimer() {
        if (timerJob?.isActive == true) return
        val startTime = Clock.System.now().toEpochMilliseconds() - _timer.value
        timerJob = viewModelScope.launch {
            while(true) {
                val currentTime = Clock.System.now().toEpochMilliseconds()
                _timer.value = currentTime - startTime
                delay(1000)
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        _timer.value = 0
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }

}