package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gymapp.composeapp.generated.resources.Res
import gymapp.composeapp.generated.resources.activity
import gymapp.composeapp.generated.resources.calories
import gymapp.composeapp.generated.resources.days
import gymapp.composeapp.generated.resources.mins
import gymapp.composeapp.generated.resources.weekly_goal
import org.jetbrains.compose.resources.stringResource


@Composable
@Preview
fun CardActivity(modifier: Modifier = Modifier) {

    Card(modifier = modifier) {

        Column(
            modifier = Modifier.padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = stringResource(Res.string.activity),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                )
                Text(
                    text = stringResource(Res.string.weekly_goal),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                
                CircularProgress(
                    title = stringResource(Res.string.calories),
                    progress = .7f,
                    progressColor = Color(0xFF1348EA)

                )

                CircularProgress(
                    title = stringResource(Res.string.days),
                    progress = .8f,
                    progressColor = Color(0xFF22D1EC)

                )

                CircularProgress(
                    title = stringResource(Res.string.mins),
                    progress = .4f,
                    progressColor = Color(0xFF34D198)

                )

            }
        }

    }
}