package com.grimco.gymapp.presenter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview(showBackground = true)
@Composable
fun Stats(modifier: Modifier = Modifier) {

    Card(
        modifier = modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row {
                Text(text = "Workout Time", modifier = Modifier.weight(1f))
                Text(text = "Calories", modifier = Modifier.weight(1f))
            }
            Row {
                Text(text = "01:23:42", modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "345cal", modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Text(text = "Total Calories", modifier = Modifier.weight(1f))
                Text(text = "Heart Rate", modifier = Modifier.weight(1f))
            }
            Row {
                Text(text = "1250cal", modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "115bpm", modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

        }

    }
}