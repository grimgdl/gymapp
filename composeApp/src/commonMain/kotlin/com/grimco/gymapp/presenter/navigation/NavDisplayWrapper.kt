package com.grimco.gymapp.presenter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.serialization.NavBackStackSerializer
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.grimco.gymapp.presenter.screens.MainScreen
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
                }
            }
        },
        Route.Main
    )


    NavDisplay(
        backStack = navBackStack,
        entryProvider = entryProvider {

            entry<Route.Main> {
                MainScreen()
            }

            entry<Route.Login> {

            }

        },
        modifier = modifier
    )


}