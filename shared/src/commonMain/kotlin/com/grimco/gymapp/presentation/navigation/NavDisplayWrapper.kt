package com.grimco.gymapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.grimco.gymapp.presentation.screens.CreateRoutineScreen
import com.grimco.gymapp.presentation.screens.EditTrainingScreen
import com.grimco.gymapp.presentation.screens.LoginScreen
import com.grimco.gymapp.presentation.screens.MainScreen
import com.grimco.gymapp.presentation.screens.RoutineDetailScreen
import com.grimco.gymapp.presentation.screens.TrainingListScreen
import com.grimco.gymapp.presentation.screens.WorkoutHistoryScreen
import com.grimco.gymapp.presentation.screens.WorkoutSessionScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Composable
fun NavDisplayWrapper(modifier: Modifier = Modifier) {
    val navBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Main::class)
                    subclass(Route.Login::class)
                    subclass(Route.RoutineMaker::class)
                    subclass(Route.RoutineDetail::class)
                    subclass(Route.TrainingList::class)
                    subclass(Route.EditTraining::class)
                    subclass(Route.WorkoutSession::class)
                    subclass(Route.WorkoutHistory::class)
                }
            }
        },
        Route.Main
    )

    NavDisplay(
        backStack = navBackStack,
        entryDecorators = listOf(
            rememberViewModelStoreNavEntryDecorator(),
            rememberSaveableStateHolderNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Main> {
                MainScreen(
                    onTraining = {
                        navBackStack.add(Route.RoutineDetail(it.id, it.discipline, it.minutes))
                    },
                    onNavigation = { navBackStack.add(it) }
                )
            }
            entry<Route.Login> { LoginScreen() }
            entry<Route.RoutineMaker> { CreateRoutineScreen() }
            entry<Route.RoutineDetail> { route ->
                RoutineDetailScreen(
                    routineId = route.routineId,
                    onStartWorkout = { navBackStack.add(Route.WorkoutSession(route.routineId)) }
                )
            }
            entry<Route.TrainingList> {
                TrainingListScreen(onEdit = {
                    navBackStack.add(Route.EditTraining(it.training.id))
                })
            }
            entry<Route.EditTraining> { route -> EditTrainingScreen(route.idTraining) }
            entry<Route.WorkoutSession> { route ->
                WorkoutSessionScreen(
                    routineId = route.routineId,
                    onBack = { if (navBackStack.size > 1) navBackStack.removeAt(navBackStack.lastIndex) }
                )
            }
            entry<Route.WorkoutHistory> { WorkoutHistoryScreen() }
        },
        modifier = modifier
    )
}
