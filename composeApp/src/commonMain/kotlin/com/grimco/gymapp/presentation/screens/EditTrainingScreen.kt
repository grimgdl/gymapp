package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.grimco.gymapp.presentation.components.TextHeader
import com.grimco.gymapp.presentation.viewmodel.EditTrainingViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditTrainingScreen(
    idTraining: Long,
    modifier: Modifier = Modifier
) {
    val viewModel: EditTrainingViewModel = koinViewModel(parameters = { parametersOf(idTraining) })
    val discipline by viewModel.disciplineEditor.input.collectAsStateWithLifecycle()
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

        Spacer(modifier = Modifier.height(80.dp))
        Row {
            TextHeader(text = "Exercise List")
        }

    }

    LaunchedEffect(discipline) {
        discipline?.let {
            if (trainingState.text.toString() != it) {
                trainingState.edit {
                    replace(0, length, it)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { trainingState.text.toString() }
            .collect { viewModel.disciplineEditor.update(it) }
    }
}