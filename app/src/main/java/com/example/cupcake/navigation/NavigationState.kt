package com.example.cupcake.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            launchSingleTop = true
            restoreState = true
            if (route == Screen.Start.route) {
                popUpTo(0) {
                    saveState = false
                }
            }
        }
    }

    fun navigateBack() {
        if (navHostController.previousBackStackEntry != null) {
            navHostController.popBackStack()
        } else {
            navHostController.navigate(Screen.Start.route) {
                popUpTo(Screen.Start.route) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}