package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grimco.gymapp.data.dtos.Exercise
import com.grimco.gymapp.data.model.ExercisesEntity


@Composable
fun ExerciseCardContainer(
    list: List<ExercisesEntity>,
    onAdd: (ExercisesEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(16.dp)
    ) {
        items(items = list) { exercise ->
            ExerciseCardSelect(
                exercise = Exercise(
                    id = exercise.id,
                    name = exercise.name
                ),
               onAdd = {
                   onAdd(exercise)
               }
            )
        }
    }
}