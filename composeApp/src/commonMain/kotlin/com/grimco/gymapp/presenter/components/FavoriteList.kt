package com.grimco.gymapp.presenter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.grimco.gymapp.model.TrainingEntity


@Composable
fun FavoriteList(list: List<Any>,modifier: Modifier = Modifier) {

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ){
        items(list){ item ->
            when(item) {
                is TrainingEntity -> {
                    Training(
                        image = item.image,
                        name = item.name
                    )
                }
            }
        }
    }
}