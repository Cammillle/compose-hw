package com.example.cupcake.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cupcake.model.OrderEvent
import com.example.cupcake.model.OrderViewModel
import com.example.cupcake.navigation.AppNavGraph
import com.example.cupcake.navigation.Screen
import com.example.cupcake.navigation.rememberNavigationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val viewModel: OrderViewModel = viewModel()
    val state = viewModel.homeState.collectAsStateWithLifecycle()

    val navigationState = rememberNavigationState()
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        topBar = {
            TopAppBarForCurrentScreen(
                currentDestination = currentDestination,
                onNavigateBack = { navigationState.navigateBack() })
        }) { paddingValues ->

        AppNavGraph(
            modifier = Modifier.padding(paddingValues),
            navigationState = navigationState,
            homeState = state.value,
            onOrderEvent = { event ->
                when (event) {
                    OrderEvent.CancelOrder -> viewModel.resetOrder()
                    is OrderEvent.SetDate -> viewModel.setDate(event.date)
                    is OrderEvent.SetFlavor -> viewModel.setFlavor(event.flavor)
                    is OrderEvent.SetQuantity -> viewModel.setQuantity(event.quantity)
                }
            },
            dateOptions = viewModel.dateOptions
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarForCurrentScreen(
    currentDestination: NavDestination?,
    onNavigateBack: () -> Unit
) {
    when (currentDestination?.route) {
        Screen.Start.route -> {
            TopAppBar(
                title = {
                    Text(
                        text = "Cupcake",
                        style = MaterialTheme.typography.titleLarge
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        Screen.Flavor.route -> {
            TopAppBar(
                title = {
                    Text(
                        text = "Choose Flavor", style = MaterialTheme.typography.titleLarge
                    )
                }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        Screen.Pickup.route -> {
            TopAppBar(
                title = {
                    Text(
                        text = "Choose Pickup Date", style = MaterialTheme.typography.titleLarge
                    )
                }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        Screen.Summary.route -> {
            TopAppBar(
                title = {
                    Text(
                        text = "Order Summary", style = MaterialTheme.typography.titleLarge
                    )
                }, navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}