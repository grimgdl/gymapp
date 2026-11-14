package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.grimco.gymapp.data.model.WorkoutSetEntity
import com.grimco.gymapp.presentation.utils.convertToTime
import com.grimco.gymapp.presentation.viewmodel.WorkoutSessionViewModel
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock

@Composable
fun WorkoutSessionScreen(
    routineId: Long,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutSessionViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val session by viewModel.session.collectAsStateWithLifecycle()
    var now by remember { mutableLongStateOf(Clock.System.now().toEpochMilliseconds()) }
    var confirmFinish by remember { mutableStateOf(false) }

    LaunchedEffect(routineId) { viewModel.prepare(routineId) }
    LaunchedEffect(session?.session?.startedAt) {
        while (true) {
            now = Clock.System.now().toEpochMilliseconds()
            delay(1_000)
        }
    }

    uiState.draftToResolve?.let { draft ->
        AlertDialog(
            onDismissRequest = onBack,
            title = { Text("Hay un entrenamiento pendiente") },
            text = { Text("¿Quieres retomar ${draft.routineName} o descartarlo para empezar esta rutina?") },
            confirmButton = {
                TextButton(onClick = viewModel::resumeDraft) { Text("Retomar borrador") }
            },
            dismissButton = {
                Row {
                    TextButton(onClick = onBack) { Text("Cancelar") }
                    TextButton(onClick = { viewModel.discardDraftAndStart(routineId) }) { Text("Descartar y empezar") }
                }
            }
        )
    }

    if (uiState.loading) {
        Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
        }
    } else if (uiState.error != null) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(uiState.error ?: "")
            TextButton(onClick = onBack) { Text("Volver") }
        }
    } else if (session != null) {
        val active = requireNotNull(session)
        Column(
            modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)
        ) {
            Text(active.session.routineName, style = MaterialTheme.typography.headlineSmall)
            Text(
                "Tiempo: ${((now - active.session.startedAt).coerceAtLeast(0L)).convertToTime()}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(16.dp))
            if (active.exercises.isEmpty()) {
                Text("Esta rutina todavía no tiene ejercicios.")
            }
            active.exercises.sortedBy { it.exercise.position }.forEach { exercise ->
                Card(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(exercise.exercise.exerciseName, style = MaterialTheme.typography.titleMedium)
                        exercise.exercise.machineName?.let { machineName ->
                            Text(
                                "Máquina: $machineName",
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        exercise.sets.sortedBy { it.setNumber }.forEach { set ->
                            SetEditor(set = set, onUpdate = viewModel::updateSet)
                        }
                    }
                }
            }
            Button(
                onClick = { confirmFinish = true },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Finalizar entrenamiento") }
        }
    }

    if (confirmFinish) {
        AlertDialog(
            onDismissRequest = { confirmFinish = false },
            title = { Text("Finalizar entrenamiento") },
            text = { Text("La sesión se guardará en el historial.") },
            confirmButton = {
                TextButton(onClick = {
                    confirmFinish = false
                    viewModel.finishSession(onBack)
                }) { Text("Finalizar") }
            },
            dismissButton = { TextButton(onClick = { confirmFinish = false }) { Text("Seguir entrenando") } }
        )
    }
}

@Composable
private fun SetEditor(set: WorkoutSetEntity, onUpdate: (WorkoutSetEntity) -> Unit) {
    var weightText by remember(set.id) { mutableStateOf(set.weightKg.takeIf { it > 0.0 }?.toString() ?: "") }
    val textDecoration = if (set.completed) TextDecoration.LineThrough else TextDecoration.None
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Serie ${set.setNumber}",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge.copy(textDecoration = textDecoration)
        )
        Text(
            text = "${set.reps} reps",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge.copy(textDecoration = textDecoration)
        )
        OutlinedTextField(
            value = weightText,
            onValueChange = {
                weightText = it
                onUpdate(set.copy(weightKg = it.toDoubleOrNull() ?: 0.0))
            },
            label = {
                Text(
                    text = "Kg",
                    style = MaterialTheme.typography.bodySmall.copy(textDecoration = textDecoration)
                )
            },
            modifier = Modifier.weight(1f),
            enabled = !set.completed,
            textStyle = MaterialTheme.typography.bodyLarge.copy(textDecoration = textDecoration),
            singleLine = true
        )
        Checkbox(checked = set.completed, onCheckedChange = { onUpdate(set.copy(completed = it)) })
    }
}
