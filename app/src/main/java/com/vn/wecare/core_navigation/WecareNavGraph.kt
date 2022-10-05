package com.vn.wecare.core_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vn.wecare.core_navigation.GraphRootItem
import com.vn.wecare.feature.navigation.NavigationBarScreen

@Composable
fun WecareNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = WecareGraphItem.WECARE,
        startDestination = NavigationBarScreen.Account.route
    ) {
        // TODO Add the Account nav graph

    }
}

object WecareGraphItem {
    const val WECARE = "WECARE"
    const val AUTHENTICATION = "authentication"
    const val HOME = "home_graph"
    const val TRAINING = "training_graph"
    const val ACCOUNT = "account_graph"
}