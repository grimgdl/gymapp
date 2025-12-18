package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun TrainingComp(
    image: Any = "https://www.mensfitness.com/.image/w_750,q_auto:good,c_limit/MjEzNzgzNTQ5NTIwNjUyMTI1/athlete-muscular-fitness-male-model-pulling-up-on-horizontal-bar.jpg?arena_f_auto",
    discipline: String = "Training",
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .size(150.dp)
            .clip(RoundedCornerShape(10.dp))
    ){

        AsyncImage(
            model = image,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = discipline,
            style = TextStyle(
                fontSize = 22.sp,
                color = Color.White,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(0f, 2f),
                    blurRadius = 2f
                )

            ),
            modifier = Modifier.padding(16.dp)

        )
    }

}