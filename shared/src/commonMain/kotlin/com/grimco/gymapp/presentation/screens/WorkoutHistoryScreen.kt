package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.grimco.gymapp.data.dtos.WorkoutSessionWithExercises
import com.grimco.gymapp.presentation.utils.convertToTime
import com.grimco.gymapp.presentation.viewmodel.WorkoutHistoryViewModel
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Instant

@Composable
fun WorkoutHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkoutHistoryViewModel = koinViewModel()
) {
    val sessions by viewModel.sessions.collectAsStateWithLifecycle()
    var expandedId by remember { mutableStateOf<Long?>(null) }

    Column(modifier = modifier.padding(16.dp)) {
        Text("Historial", style = MaterialTheme.typography.headlineSmall)
        if (sessions.isEmpty()) {
            Text("Aún no hay entrenamientos finalizados.", modifier = Modifier.padding(top = 16.dp))
        } else {
            LazyColumn {
                items(sessions, key = { it.session.id }) { item ->
                    SessionHistoryCard(
                        item = item,
                        expanded = expandedId == item.session.id,
                        onClick = {
                            expandedId = if (expandedId == item.session.id) null else item.session.id
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SessionHistoryCard(
    item: WorkoutSessionWithExercises,
    expanded: Boolean,
    onClick: () -> Unit
) {
    val started = Instant.fromEpochMilliseconds(item.session.startedAt).toString().replace('T', ' ').substringBefore('.')
    val duration = ((item.session.endedAt ?: item.session.startedAt) - item.session.startedAt)
        .coerceAtLeast(0L).convertToTime()

    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp).clickable(onClick = onClick)) {
        Column(Modifier.padding(16.dp)) {
            Text(item.session.routineName, style = MaterialTheme.typography.titleMedium)
            Text(started)
            Text("Duración: $duration · ${item.exercises.size} ejercicios")
            if (expanded) {
                Spacer(Modifier.padding(4.dp))
                item.exercises.sortedBy { it.exercise.position }.forEach { exercise ->
                    Text(exercise.exercise.exerciseName, style = MaterialTheme.typography.titleSmall)
                    exercise.exercise.machineName?.let { machineName ->
                        Text("Máquina: $machineName")
                    }
                    exercise.sets.sortedBy { it.setNumber }.forEach { set ->
                        Text("Serie ${set.setNumber}: ${set.reps} reps · ${set.weightKg} kg")
                    }
                }
            }
        }
    }
}
