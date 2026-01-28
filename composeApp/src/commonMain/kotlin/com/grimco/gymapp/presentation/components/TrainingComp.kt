package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
@Preview
fun TrainingComp(
    image: Any = "https://www.mensfitness.com/.image/w_750,q_auto:good,c_limit/MjEzNzgzNTQ5NTIwNjUyMTI1/athlete-muscular-fitness-male-model-pulling-up-on-horizontal-bar.jpg?arena_f_auto",
    discipline: String = "Training",
    minutes: String = "25 mins",
    intensity: String = "High Intensity",
    modifier: Modifier = Modifier
) {


    Column {
        Box(
            modifier = modifier
                .size(200.dp)
                .clip(RoundedCornerShape(10.dp))
        ){

            AsyncImage(
                model = image,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = discipline,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$minutes ¤ $intensity",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }



}