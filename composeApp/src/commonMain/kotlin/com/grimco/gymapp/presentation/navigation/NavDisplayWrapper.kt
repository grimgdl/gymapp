package com.grimco.gymapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.grimco.gymapp.presentation.screens.CreateRoutineScreen
import com.grimco.gymapp.presentation.screens.EditTrainingScreen
import com.grimco.gymapp.presentation.screens.LoginScreen
import com.grimco.gymapp.presentation.screens.MainScreen
import com.grimco.gymapp.presentation.screens.RoutineDetailScreen
import com.grimco.gymapp.presentation.screens.TrainingListScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass


@Composable
fun NavDisplayWrapper(modifier: Modifier = Modifier) {
    val navBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration{
            serializersModule = SerializersModule{
                polymorphic(NavKey::class) {
                    subclass(Route.Main::class)
                    subclass(Route.Login::class)
                    subclass(Route.RoutineMaker::class)
                    subclass(Route.RoutineDetail::class)
                    subclass(Route.TrainingList::class)
                    subclass(Route.EditTraining::class)
                }
            }
        },
        Route.Main
    )


    NavDisplay(
        backStack = navBackStack,
        entryProvider = entryProvider {

            entry<Route.Main> {
                MainScreen(
                    onTraining = {
                        navBackStack.add(Route.RoutineDetail(routineId = it.id, routineName = it.discipline, minutes = it.minutes))
                    },
                    onNavigation = {
                        navBackStack.add(it)
                    }
                )
            }
            entry<Route.Login> {
                LoginScreen()
            }
            entry<Route.RoutineMaker> {
                CreateRoutineScreen()
            }

            entry<Route.RoutineDetail> { navBackStack ->
                RoutineDetailScreen(navBackStack.routineId, navBackStack.routineName, navBackStack.minutes)
            }
            entry<Route.TrainingList> {
                TrainingListScreen(
                    onEdit = {
                        navBackStack.add(Route.EditTraining(it.training.id))
                    }
                )
            }

            entry<Route.EditTraining>{ back ->
                EditTrainingScreen(back.idTraining)
            }


        },
        modifier = modifier
    )

}