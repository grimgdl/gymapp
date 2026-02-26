package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grimco.gymapp.data.dtos.Exercise


@Composable
fun ExerciseCardSelect(
    exercise: Exercise,
    isSelected: Boolean = false,
    onCheck: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isChecked by remember(isSelected) { mutableStateOf(isSelected) }
    Card(
        modifier = modifier
            .padding(bottom = 3.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = exercise.name)
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onCheck(it)
                }
            )
        }
    }

}