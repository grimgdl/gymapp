package com.grimco.gymapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.grimco.gymapp.presentation.navigation.NavDisplayWrapper
import com.grimco.gymapp.presentation.theme.GymAppTheme

@Composable
@Preview
fun App() {
    GymAppTheme {

        Surface {
            Scaffold { paddingValues ->
                NavDisplayWrapper(modifier = Modifier.padding(paddingValues = paddingValues))
            }
        }
    }
}