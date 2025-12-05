package com.grimco.gymapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.grimco.gymapp.model.TrainingEntity
import com.grimco.gymapp.presenter.components.FavoriteList
import com.grimco.gymapp.presenter.components.Stats
import com.grimco.gymapp.ui.theme.MaterialGym
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview(showBackground = true, )
fun App() {
    MaterialGym(
    ) {

        val favoriteList = remember { listOf(
            TrainingEntity(id = 1, name = "Leg", image = "https://cdn.shopify.com/s/files/1/0568/6280/2107/files/fentes_bulgares_600x600.jpg?v=1705571601"),
            TrainingEntity(id = 3, name = "functional", image = "https://cdn.shopify.com/s/files/1/0568/6280/2107/files/hip_trust_600x600.jpg?v=1705571653"),
            TrainingEntity(id = 4, name = "Speed", image = "https://res.cloudinary.com/display97/image/upload/87196.jpg"),
        ) }

        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "favorite"
            )

            FavoriteList(
                list = favoriteList
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Stats"
            )

            Spacer(modifier = Modifier.height(15.dp))
            Stats()

        }


    }
}
