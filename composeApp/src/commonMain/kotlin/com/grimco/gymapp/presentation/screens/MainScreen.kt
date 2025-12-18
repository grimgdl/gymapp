package com.grimco.gymapp.presentation.screens


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grimco.gymapp.data.model.Training
import com.grimco.gymapp.presentation.components.FavoriteTraining
import com.grimco.gymapp.presentation.components.StatComponent
import kotlin.collections.listOf


@Composable
fun MainScreen(modifier: Modifier = Modifier) {

    val favoriteList = remember {
        listOf(
            Training(discipline = "Back", image = "https://www.mensfitness.com/.image/w_750,q_auto:good,c_limit/MjEzNzgzNTQ5NTIwNjUyMTI1/athlete-muscular-fitness-male-model-pulling-up-on-horizontal-bar.jpg?arena_f_auto"),
            Training(discipline = "legs", image = "https://cdn.muscleandstrength.com/sites/default/files/images/articles/leg-training-for-women-split-squat.jpg"),
            Training(discipline = "Arm", image = "https://www.trainheroic.com/wp-content/uploads/2023/01/AdobeStock_298511434-TH-jpg.webp"),
            Training(discipline = "chest", image = "https://cdn.muscleandstrength.com/sites/default/files/field/feature-image/workout/10mass_feature.jpg")
        )
    }

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {

        Text(
            text ="Favorites",
            style = TextStyle(
                fontSize = 22.sp
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        FavoriteTraining(favoriteList = favoriteList)

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text ="Stats",
            style = TextStyle(
                fontSize = 22.sp
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        StatComponent()


    }



}