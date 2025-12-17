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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cupcake.R
import com.example.cupcake.model.OrderEvent
import com.example.cupcake.model.OrderViewModel
import com.example.cupcake.navigation.AppNavGraph
import com.example.cupcake.navigation.Screen
import com.example.cupcake.navigation.rememberNavigationState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSendOrder: (String) -> Unit
) {
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
            dateOptions = viewModel.dateOptions,
            sendOrder = { orderText ->
                onSendOrder(orderText)
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarForCurrentScreen(
    currentDestination: NavDestination?,
    onNavigateBack: () -> Unit
) {
    val (titleResId, showBackButton) = remember(currentDestination) {
        when (currentDestination?.route) {
            Screen.Start.route -> R.string.order_cupcakes to false
            Screen.Flavor.route -> R.string.choose_flavor to true
            Screen.Pickup.route -> R.string.choose_pickup_date to true
            Screen.Summary.route -> R.string.order_summary to true
            else -> null to false
        }
    }
    val title = if (titleResId != null) {
        stringResource(id = titleResId)
    } else {
        ""
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}