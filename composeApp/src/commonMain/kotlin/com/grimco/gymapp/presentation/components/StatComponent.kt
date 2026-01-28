package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun StatComponent(modifier: Modifier = Modifier) {


    Column(
        modifier = modifier
    ) {
        CardActivity()

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CardStatParam(
                title = "CALORIES",
                content = "1,240 kcal",
                percent = "+12",
                modifier = Modifier.weight(1f)
            )

            CardStatParam(
                title = "WORKOUTS",
                content = "4/5 days",
                percent = "-12",
                modifier = Modifier.weight(1f)
            )
        }



    }


}