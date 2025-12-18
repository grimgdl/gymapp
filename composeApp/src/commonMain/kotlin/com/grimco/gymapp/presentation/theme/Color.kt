package com.grimco.gymapp.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


val darkBackground = Color(0xFF343434)
val cardDarkBackground = Color(0xFF2F405A)

val lightBackground = Color(0xFFF8F8F8)
val cardLightBackground = Color(0xFFEAEAEA)


val DarkColorScheme = darkColorScheme(
    background = darkBackground,
    surface = darkBackground,
    surfaceVariant = cardDarkBackground
)

val LightColorScheme = lightColorScheme(
    background = lightBackground,
    surface = lightBackground,
    surfaceVariant = cardLightBackground
)