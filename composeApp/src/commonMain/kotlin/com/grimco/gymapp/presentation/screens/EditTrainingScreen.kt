package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.grimco.gymapp.presentation.viewmodel.EditTrainingViewModel
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditTrainingScreen(
    idTraining: Long,
    modifier: Modifier = Modifier
) {
    val viewModel: EditTrainingViewModel = koinViewModel(parameters = { parametersOf(idTraining) })
    val training by viewModel.training.collectAsStateWithLifecycle()
    val trainingState = rememberTextFieldState( "")

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            state = trainingState,
            label = {
                Text("Training Name")
            },
            modifier = Modifier.fillMaxWidth()
        )
    }


    LaunchedEffect(trainingState.text) {
        if(trainingState.text.isBlank()) return@LaunchedEffect
        delay(500)
        training?.training?.let {
            viewModel.updateTraining(it.copy(discipline = trainingState.text.toString()))
        }
    }

    LaunchedEffect(training) {
        trainingState.edit {
            replace(0, length, training?.training?.discipline ?: "")
        }
    }
}