package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.grimco.gymapp.domain.model.GymMachine

@Composable
fun EquipmentList(
    modifier: Modifier = Modifier,
    machines: List<GymMachine>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
    ) {
        items(items = machines) {
            Card {
                Text(it.name)
            }
        }
    }
}