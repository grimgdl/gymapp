package com.grimco.gymapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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

@Composable
@Preview
fun CardStatParam(
    title: String,
    content: String,
    percent: String,
    modifier: Modifier = Modifier
) {


    val color = if(percent.contains("+")) Color(0xFF09E364) else Color(0xFFF76338)

    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {

            Text(
                text = title,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {

                Icon(
                    imageVector = Icons.AutoMirrored.Default.TrendingDown,
                    contentDescription = null,
                    tint = color
                )
                Text(
                    text = "$percent%",
                    color = color,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
    }


}