package com.grimco.gymapp.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
sealed interface Route: NavKey {
    @Serializable
    data object Main: Route
    @Serializable
    data object Login: Route
    @Serializable
    data object  RoutineMaker: Route
    @Serializable
    data class RoutineDetail(val routineId: Long, val routineName: String, val minutes: String): Route
}