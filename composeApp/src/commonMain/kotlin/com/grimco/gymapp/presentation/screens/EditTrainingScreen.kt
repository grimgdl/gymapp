package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.grimco.gymapp.presentation.viewmodel.EditTrainingViewModel
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
        modifier = modifier
    ) {
        OutlinedTextField(
            state = trainingState,
            label = {
                Text("label")
            }
        )
    }

    LaunchedEffect(training) {

        println("data launched effect $training")


        trainingState.edit {
            replace(0, length, training?.training?.discipline ?: "")
        }
    }
}