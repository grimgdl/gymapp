package com.grimco.gymapp.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay


@Composable
fun <T: CharSequence> DeBounceEffect(
    value: T,
    delayMillis: Long = 500,
    onDebounced: (T) -> Unit
) {
    LaunchedEffect(value) {
        delay(delayMillis)
        onDebounced(value)
    }
}