package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grimco.gymapp.data.dtos.TrainingExercise
import com.grimco.gymapp.presentation.viewmodel.TrainingViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun TrainingListScreen(
    trainingViewModel: TrainingViewModel = koinViewModel(),
    onEdit: (item: TrainingExercise) -> Unit,
    modifier: Modifier = Modifier
) {
    val training by trainingViewModel.trainingList.collectAsState()
    Column(
        modifier = modifier.padding(16.dp)
    ){
        LazyColumn{

            items(items = training) { item ->

                Card(
                    modifier = Modifier.fillMaxWidth()
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
    }

}