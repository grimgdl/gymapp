package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExerciseRoutineCard(
    name: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(bottom = 3.dp)) {
        Row(
            modifier = Modifier.padding(16.dp)
                .fillMaxWidth()
        ) {
            TextHeader(name)
        }
    }
}