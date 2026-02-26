package com.grimco.gymapp.presentation.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class FlowEditableField<T>(
    source: Flow<T>,
    scope: CoroutineScope,
    private val debounceMillis: Long = 500,
    private val onCommit: suspend (T) -> Unit
) {

    private  val _input = MutableStateFlow<T?>(null)
    val input: StateFlow<T?> = _input

    init {
        scope.launch {
            source
                .distinctUntilChanged()
                .collect { value ->
                    _input.value = value
                }
        }

        scope.launch {
            _input
                .filterNotNull()
                .drop(1)
                .debounce(debounceMillis)
                .distinctUntilChanged()
                .collectLatest { value -> onCommit(value) }
        }
    }
    fun update(value: T) {
        _input.value = value
    }

}