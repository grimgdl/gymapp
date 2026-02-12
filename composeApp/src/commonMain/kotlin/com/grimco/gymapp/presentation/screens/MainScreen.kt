package com.grimco.gymapp.presentation.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grimco.gymapp.data.model.Training
import com.grimco.gymapp.presentation.components.CardWorkout
import com.grimco.gymapp.presentation.components.FavoriteTraining
import com.grimco.gymapp.presentation.components.StatComponent
import com.grimco.gymapp.presentation.viewmodel.MainViewmodel
import io.ktor.utils.io.InternalAPI
import org.koin.compose.viewmodel.koinViewModel


@OptIn(InternalAPI::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onTraining: (training: Training) -> Unit = {},
    viewmodel: MainViewmodel = koinViewModel()
) {

    val scrollState = rememberScrollState()
    val name = remember { "Gustavo" }

    val favoriteList = remember {
        listOf(
            Training(discipline = "Back", image = "https://www.mensfitness.com/.image/w_750,q_auto:good,c_limit/MjEzNzgzNTQ5NTIwNjUyMTI1/athlete-muscular-fitness-male-model-pulling-up-on-horizontal-bar.jpg?arena_f_auto", minutes = "25 mins", intensity = "High power"),
            Training(discipline = "legs", image = "https://cdn.muscleandstrength.com/sites/default/files/images/articles/leg-training-for-women-split-squat.jpg", minutes = "15 mins", intensity = "middle power"),
            Training(discipline = "Arm", image = "https://www.trainheroic.com/wp-content/uploads/2023/01/AdobeStock_298511434-TH-jpg.webp", minutes = "13 mins", intensity = "High power"),
            Training(discipline = "chest", image = "https://cdn.muscleandstrength.com/sites/default/files/field/feature-image/workout/10mass_feature.jpg", minutes = "15 mins", intensity = "High power")
        )
    }

    Column(
        modifier = modifier.padding(16.dp)
            .verticalScroll(scrollState)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Column(
                modifier = Modifier.padding(start = 20.dp)
            ) {
                Text(
                    text = "GOOD MORNING",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {},

            ){
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        StatComponent()


        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Favorite Routines", style = TextStyle(
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )

            TextButton(onClick = {}) {
                Text(
                    "See All",
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        Spacer(modifier = Modifier.height(30.dp))

        FavoriteTraining(favoriteList = favoriteList) {
            onTraining(it)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Next Workout",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        CardWorkout()
    }

}


@PreviewLightDark
@Preview(showSystemUi = true)
@Composable
fun MyPreview() {
    Row {
        Text("hola")
    }
}