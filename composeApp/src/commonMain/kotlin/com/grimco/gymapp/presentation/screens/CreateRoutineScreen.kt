package com.grimco.gymapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grimco.gymapp.presentation.components.TextHeader

@Composable
@Preview(showBackground = true)
fun CreateRoutineScreen(modifier: Modifier = Modifier) {

    var routineName by remember { mutableStateOf("") }


    Column(
        modifier = modifier.padding(16.dp)
    ) {

        TextHeader(text = "ROUTINE NAME")
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = routineName,
            onValueChange = { routineName = it },
            placeholder = {
                Text("Upper Body Power")
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextHeader(text = "CATEGORY")
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            FilterChip(
                onClick = {},
                label = {
                    Text("Strength")
                },
                selected = true
            )

            FilterChip(
                onClick = {},
                label = {
                    Text("Cardio")
                },
                selected = false
            )

            FilterChip(
                onClick = {},
                label = {
                    Text("Yoga")
                },
                selected = false
            )

            FilterChip(
                onClick = {},
                label = {
                    Text("HIIT")
                },
                selected = false
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextHeader(text = "EXERCISES")
            TextButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Text(text = "Add New")
            }
        }


    }

}