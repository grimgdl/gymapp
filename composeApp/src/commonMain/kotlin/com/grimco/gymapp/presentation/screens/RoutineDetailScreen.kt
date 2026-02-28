package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.grimco.gymapp.presentation.components.ExerciseRoutineCard
import com.grimco.gymapp.presentation.components.TextHeader
import com.grimco.gymapp.presentation.viewmodel.DetailTrainingViewModel
import com.grimco.gymapp.presentation.viewmodel.TrainingViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun RoutineDetailScreen(
    routineId: Long = 0,
    routineName: String = "Push Day A",
    minutes: String = "45 mins",
    modifier: Modifier = Modifier
) {

    val viewModel: DetailTrainingViewModel = koinViewModel( parameters = { parametersOf(routineId) })
    val training by viewModel.training.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
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
            Column(
                modifier = Modifier.padding(start = 20.dp)
            ) {
                TextHeader(routineName)
                Text(text = minutes, color = MaterialTheme.colorScheme.secondary)
                Text(text = "Chest, Shoulders, Triceps", color = MaterialTheme.colorScheme.tertiary)
            }
        }

       Spacer(modifier = Modifier.height(20.dp))

        Card {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    imageVector = Icons.Default.Timer,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text( text = "Active time", color = MaterialTheme.colorScheme.secondary)
                    TextHeader("28:18")
                }

                Spacer(modifier = Modifier.weight(1f))
                Row {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Pause, contentDescription = null)
                    }
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Stop, contentDescription = null)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            TextHeader(text = "Exercises")
            Text(text = "${training?.exercises?.size} TOTAL", color = MaterialTheme.colorScheme.secondary)
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            training?.exercises?.let {
                items(items = it) { item ->
                    ExerciseRoutineCard(item.name)
                }
            }
        }
    }
}