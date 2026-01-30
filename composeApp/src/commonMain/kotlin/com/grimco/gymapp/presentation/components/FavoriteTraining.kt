package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grimco.gymapp.data.model.Training


@Composable
fun FavoriteTraining(
    favoriteList: List<Training>,
    modifier: Modifier = Modifier,
    onClick: (data: Training) -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(favoriteList) { training ->

            TrainingComp(data = training) { data ->
                onClick(data)
            }
        }

    }
}