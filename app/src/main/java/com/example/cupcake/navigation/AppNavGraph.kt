package com.example.cupcake.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cupcake.screens.FlavorScreen
import com.example.cupcake.screens.PickupScreen
import com.example.cupcake.screens.StartScreen
import com.example.cupcake.screens.SummaryScreen

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    navigationState: NavigationState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Start.route
    ) {
        composable(route = Screen.Start.route) {
            StartScreen(
                modifier = modifier.fillMaxSize(),
                onOrderCupcake = {
                    navigationState.navigateTo(Screen.Flavor.route)
                }
            )
        }
        composable(route = Screen.Flavor.route) {
            FlavorScreen(
                modifier = modifier.fillMaxSize(),
                onCancelOrder = {
                    navigationState.navigateTo(Screen.Start.route)
                },
                onNextButtonClicked = {
                    navigationState.navigateTo(Screen.Pickup.route)
                }
            )
        }
        composable(route = Screen.Pickup.route) {
            PickupScreen(
                modifier = modifier.fillMaxSize(),
                onCancelOrder = {
                    navigationState.navigateTo(Screen.Start.route)
                },
                onNextButtonClicked = {
                    navigationState.navigateTo(Screen.Summary.route)
                }
            )

        }
        composable(route = Screen.Summary.route) {
            SummaryScreen(
                modifier = modifier.fillMaxSize(),
                onSendOrder = {},
                onCancelOrder = {
                    navigationState.navigateTo(Screen.Start.route)
                }
            )
        }
    }
}