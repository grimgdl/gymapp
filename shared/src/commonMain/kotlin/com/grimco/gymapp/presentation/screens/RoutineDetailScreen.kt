package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.grimco.gymapp.presentation.components.TextHeader
import com.grimco.gymapp.presentation.viewmodel.DetailTrainingViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RoutineDetailScreen(
    routineId: Long = 0,
    modifier: Modifier = Modifier,
    onStartWorkout: () -> Unit = {},
    viewModel: DetailTrainingViewModel = koinViewModel(parameters = { parametersOf(routineId) })
) {
    val training by viewModel.training.collectAsStateWithLifecycle()

    Column(modifier = modifier.padding(16.dp).fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(100.dp).background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Blue, Color.Black),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, 0f)
                    )
                ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = training?.training?.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(start = 20.dp)) {
                TextHeader(training?.training?.discipline ?: "")
                Text(text = "${training?.exercises?.size ?: 0} ejercicios", color = MaterialTheme.colorScheme.secondary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onStartWorkout, modifier = Modifier.fillMaxWidth()) {
            Text("Iniciar entrenamiento")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextHeader("Ejercicios")
            Text(text = "${training?.exercises?.size ?: 0} TOTAL", color = MaterialTheme.colorScheme.secondary)
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            training?.exercises?.let { exercises ->
                items(exercises) { exercise ->
                    Card(modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)) {
                        val plan = training?.exercisePlans?.firstOrNull {
                            it.idExercise == exercise.exercise.id
                        }
                        Column(Modifier.padding(16.dp)) {
                            Text(exercise.exercise.name)
                            Text(
                                "${plan?.plannedSets ?: 3} series × ${plan?.plannedReps ?: 10} reps",
                                color = MaterialTheme.colorScheme.secondary
                            )
                            exercise.machine?.let { machine ->
                                Text(
                                    "Máquina: ${machine.name}",
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(routineId) { viewModel.updateIdTraining(routineId) }
}
