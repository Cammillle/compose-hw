package com.example.cupcake.navigation

sealed class Screen(
    val route: String
) {
    object Start : Screen(ROUTE_START)
    object Flavor : Screen(ROUTE_FLAVOR)
    object Pickup : Screen(ROUTE_PICKUP)
    object Summary : Screen(ROUTE_SUMMARY)

    companion object {
        private const val ROUTE_START = "start"
        private const val ROUTE_FLAVOR = "flavor"
        private const val ROUTE_PICKUP = "pickup"
        private const val ROUTE_SUMMARY = "summary"
    }

}