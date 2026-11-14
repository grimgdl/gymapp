package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grimco.gymapp.data.dtos.Exercise
import com.grimco.gymapp.domain.model.GymMachine

@Composable
fun ExerciseCard(
    item: Exercise,
    plannedSets: Int,
    plannedReps: Int,
    machineId: Long?,
    machines: List<GymMachine>,
    onMachineSelected: (Long?) -> Unit,
    onPlanChanged: (sets: Int, reps: Int) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var setsText by remember(item.id) { mutableStateOf(plannedSets.toString()) }
    var repsText by remember(item.id) { mutableStateOf(plannedReps.toString()) }
    var machineMenuExpanded by remember(item.id) { mutableStateOf(false) }
    val selectedMachine = machines.firstOrNull { it.id == machineId }

    var showDialogEquipment by remember { mutableStateOf(false) }

    Card(modifier = modifier.padding(bottom = 6.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = item.name, modifier = Modifier.weight(1f))
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar ejercicio")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = setsText,
                    onValueChange = {
                        setsText = it
                        it.toIntOrNull()?.takeIf { value -> value > 0 }?.let { sets ->
                            onPlanChanged(sets, repsText.toIntOrNull()?.coerceAtLeast(1) ?: plannedReps)
                        }
                    },
                    label = { Text("Series") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                OutlinedTextField(
                    value = repsText,
                    onValueChange = {
                        repsText = it
                        it.toIntOrNull()?.takeIf { value -> value > 0 }?.let { reps ->
                            onPlanChanged(setsText.toIntOrNull()?.coerceAtLeast(1) ?: plannedSets, reps)
                        }
                    },
                    label = { Text("Repeticiones") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            OutlinedButton(
                onClick = { showDialogEquipment = true },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text(selectedMachine?.name ?: "Sin máquina")
            }

            if (showDialogEquipment) {
                AlertDialog(
                    onDismissRequest = {},
                    text = {
                        EquipmentList(
                            machines = machines
                        )
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialogEquipment = false
                        }) { Text("Cancel") }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {},
                        ) {
                            Text("Confirm")
                        }
                    }
                )
            }

        }
    }
}