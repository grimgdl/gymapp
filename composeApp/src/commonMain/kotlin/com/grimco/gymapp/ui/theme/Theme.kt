package com.grimco.gymapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


val DarkColorScheme = darkColorScheme(background = backgroundDark)
val LightColorScheme = lightColorScheme(background = backgroundLight)


@Composable
fun MaterialGym(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}