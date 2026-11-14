package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.grimco.gymapp.domain.model.GymMachine
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import kotlinx.coroutines.launch

@Composable
fun MachineManagerPanel(
    machines: List<GymMachine>,
    onSave: (existing: GymMachine?, name: String, notes: String, imageBytes: ByteArray?) -> Unit,
    onDelete: (GymMachine) -> Unit,
    modifier: Modifier = Modifier
) {
    var editingMachine by remember { mutableStateOf<GymMachine?>(null) }
    var showEditor by remember { mutableStateOf(false) }
    var machineToDelete by remember { mutableStateOf<GymMachine?>(null) }

    Column(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        TextButton(onClick = {
            editingMachine = null
            showEditor = true
        }) {
            Icon(Icons.Default.Add, contentDescription = null)
            Text("Nueva máquina")
        }

        if (machines.isEmpty()) {
            Text(
                text = "Todavía no hay máquinas registradas.",
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.secondary
            )
        } else {
            LazyColumn {
                items(machines, key = { it.id }) { machine ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (!machine.imagePath.isNullOrBlank()) {
                                AsyncImage(
                                    model = machine.imagePath,
                                    contentDescription = null,
                                    modifier = Modifier.size(56.dp),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f).padding(horizontal = 12.dp)
                            ) {
                                Text(machine.name, style = MaterialTheme.typography.titleMedium)
                                machine.notes?.takeIf(String::isNotBlank)?.let {
                                    Text(it, color = MaterialTheme.colorScheme.secondary)
                                }
                            }
                            IconButton(onClick = {
                                editingMachine = machine
                                showEditor = true
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar máquina")
                            }
                            IconButton(onClick = { machineToDelete = machine }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar máquina")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showEditor) {
        var name by remember { mutableStateOf(editingMachine?.name.orEmpty()) }
        var notes by remember { mutableStateOf(editingMachine?.notes.orEmpty()) }
        var imageBytes by remember { mutableStateOf<ByteArray?>(null) }
        val context = LocalPlatformContext.current
        val scope = rememberCoroutineScope()
        val imagePicker = rememberFilePickerLauncher(
            type = FilePickerFileType.Image,
            selectionMode = FilePickerSelectionMode.Single,
            onResult = { files ->
                scope.launch {
                    files.firstOrNull()?.let { imageBytes = it.readByteArray(context) }
                }
            }
        )

        AlertDialog(
            onDismissRequest = { showEditor = false },
            title = { Text(if (editingMachine == null) "Nueva máquina" else "Editar máquina") },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre") },
                        singleLine = true
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notas (opcional)") }
                    )
                    Spacer(Modifier.height(8.dp))
                    val currentImage = imageBytes
                    if (currentImage != null || !editingMachine?.imagePath.isNullOrBlank()) {
                        AsyncImage(
                            model = currentImage ?: editingMachine?.imagePath,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().height(120.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    TextButton(onClick = { imagePicker.launch() }) {
                        Icon(Icons.Default.Image, contentDescription = null)
                        Text("Seleccionar imagen (opcional)")
                    }
                }
            },
            confirmButton = {
                TextButton(
                    enabled = name.isNotBlank(),
                    onClick = {
                        onSave(editingMachine, name.trim(), notes.trim(), imageBytes)
                        showEditor = false
                    }
                ) { Text("Guardar") }
            },
            dismissButton = {
                TextButton(onClick = { showEditor = false }) { Text("Cancelar") }
            }
        )
    }

    machineToDelete?.let { machine ->
        AlertDialog(
            onDismissRequest = { machineToDelete = null },
            title = { Text("Eliminar máquina") },
            text = { Text("Se quitará la asociación en los ejercicios que la usan.") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(machine)
                    machineToDelete = null
                }) { Text("Eliminar") }
            },
            dismissButton = {
                TextButton(onClick = { machineToDelete = null }) { Text("Cancelar") }
            }
        )
    }
}
