package com.example.cupcake.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cupcake.model.HomeState
import com.example.cupcake.model.OrderEvent
import com.example.cupcake.screens.FlavorScreen
import com.example.cupcake.screens.PickupScreen
import com.example.cupcake.screens.StartScreen
import com.example.cupcake.screens.SummaryScreen

@Composable
fun AppNavGraph(
    navigationState: NavigationState,
    onOrderEvent: (OrderEvent) -> Unit,
    homeState: HomeState,
    dateOptions: List<String>,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navigationState.navHostController,
        startDestination = Screen.Start.route
    ) {
        composable(route = Screen.Start.route) {
            StartScreen(
                modifier = modifier.fillMaxSize(),
                onOrderCupcake = { quantity ->
                    onOrderEvent(OrderEvent.SetQuantity(quantity))
                    navigationState.navigateTo(Screen.Flavor.route)
                }
            )
        }
        composable(route = Screen.Flavor.route) {
            FlavorScreen(
                modifier = modifier.fillMaxSize(),
                onCancelOrder = {
                    onOrderEvent(OrderEvent.CancelOrder)
                    navigationState.navigateTo(Screen.Start.route)
                },
                onNextButtonClicked = {
                    navigationState.navigateTo(Screen.Pickup.route)
                },
                onSelectFlavor = { flavor ->
                    onOrderEvent(OrderEvent.SetFlavor(flavor))
                },
                price = homeState.price
            )
        }
        composable(route = Screen.Pickup.route) {
            PickupScreen(
                modifier = modifier.fillMaxSize(),
                onCancelOrder = {
                    onOrderEvent(OrderEvent.CancelOrder)
                    navigationState.navigateTo(Screen.Start.route)
                },
                onNextButtonClicked = {
                    navigationState.navigateTo(Screen.Summary.route)
                },
                price = homeState.price,
                dateOptions = dateOptions,
                onDatePick = { date ->
                    onOrderEvent(OrderEvent.SetDate(date))
                }
            )
        }
        composable(route = Screen.Summary.route) {
            SummaryScreen(
                modifier = modifier.fillMaxSize(),
                onSendOrder = {},
                onCancelOrder = {
                    onOrderEvent(OrderEvent.CancelOrder)
                    navigationState.navigateTo(Screen.Start.route)
                },
                state = homeState
            )
        }
    }
}