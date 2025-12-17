package com.grimco.gymapp.presenter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatComponent(modifier: Modifier = Modifier) {

    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row {
                Text("Calories", modifier = Modifier.weight(1f))
                Text("Steps", modifier = Modifier.weight(1f))
            }

            Row {
                Text("12323cal", modifier = Modifier.weight(1f))
                Text("500steps", modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row {
                Text("Heart Rate", modifier = Modifier.weight(1f))
                Text("", modifier = Modifier.weight(1f))
            }

            Row {
                Text("165hr", modifier = Modifier.weight(1f))
                Text("", modifier = Modifier.weight(1f))
            }

        }
    }

}