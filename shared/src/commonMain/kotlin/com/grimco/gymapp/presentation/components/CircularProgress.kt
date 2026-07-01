package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun CircularProgress(
    progress: Float,
    progressColor: Color,
    title: String,
    modifier: Modifier = Modifier
) {

    val progressText = (progress * 100).toInt()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator(
                progress = {
                    progress
                },
                color = progressColor,
                modifier = Modifier.size(80.dp),
                strokeWidth = 7.dp
            )

            Text(
                text = "$progressText%",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            )

        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }


}