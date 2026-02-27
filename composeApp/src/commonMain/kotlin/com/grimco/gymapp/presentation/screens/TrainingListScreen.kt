package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grimco.gymapp.data.dtos.TrainingExercise
import com.grimco.gymapp.data.model.TrainingEntity
import com.grimco.gymapp.presentation.components.TextHeader
import com.grimco.gymapp.presentation.viewmodel.TrainingViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingListScreen(
    trainingViewModel: TrainingViewModel = koinViewModel(),
    onEdit: (item: TrainingExercise) -> Unit,
    modifier: Modifier = Modifier
) {
    val training by trainingViewModel.trainingList.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    Column(
        modifier = modifier.padding(16.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextHeader("Training list")

            TextButton(onClick = {
                showDialog = true
            }) {
                Text("New Training", color = MaterialTheme.colorScheme.tertiary)
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn{

            items(items = training) { item ->

                Card(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 3.dp)
                ){
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = item.training.discipline)
                            Text(text = "Exercises ${item.exercises.size}")
                        }

                        IconButton(onClick = {
                            onEdit(item)
                        }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                        }
                    }
                }
            }
        }

        if(showDialog) {
            val state = rememberTextFieldState("")

            BasicAlertDialog(
                onDismissRequest = {
                    showDialog = false
                    if(state.text.isNotEmpty()) {
                        scope.launch {
                            trainingViewModel.createTraining(
                                TrainingEntity(
                                    discipline = state.text.toString()
                                )
                            )
                        }
                    }
                }
            ) {
                Column {
                    OutlinedTextField(
                        state = state,
                        label = {
                            Text("New Training")
                        }
                    )
                }
            }

        }
    }

}