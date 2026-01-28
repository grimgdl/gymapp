package com.grimco.gymapp.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


val darkBackground = Color(0xFF101522)
val cardDarkBackground = Color(0xFF1A2030)
val primaryDark = Color(0xFFFDFDFD)
val secondaryDark = Color(0xFF9399A5)

val variantDark = Color(0xFF194CEA)

val lightBackground = Color(0xFFFFFFFF)
val cardLightBackground = Color(0xFF6093AC)

val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    secondary = secondaryDark,
    background = darkBackground,
    surface = darkBackground,
    surfaceContainerHighest = cardDarkBackground,
    surfaceVariant = variantDark
)

val LightColorScheme = lightColorScheme(
    background = lightBackground,
)