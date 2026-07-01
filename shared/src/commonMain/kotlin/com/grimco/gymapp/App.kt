package com.grimco.gymapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.grimco.gymapp.data.di.databaseModule
import com.grimco.gymapp.data.di.platformModule
import com.grimco.gymapp.presentation.navigation.NavDisplayWrapper
import com.grimco.gymapp.presentation.theme.GymAppTheme
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
fun App() {
    GymAppTheme {

        Surface {
            Scaffold { paddingValues ->
                NavDisplayWrapper(modifier = Modifier.padding(paddingValues = paddingValues))
            }
        }
    }
}



@Composable
@Preview
fun AppPreview() {
    KoinApplication(
        configuration = koinConfiguration {
            modules(platformModule, databaseModule)
        }
    ){
        App()
    }
}

