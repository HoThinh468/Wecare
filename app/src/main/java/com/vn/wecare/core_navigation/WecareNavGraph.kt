package com.vn.wecare.core_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vn.wecare.feature.account.accountNavGraph
import com.vn.wecare.feature.authentication.authNavGraph
import com.vn.wecare.feature.home.homeNavGraph
import com.vn.wecare.feature.training.trainingNavGraph

@Composable
fun WecareNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = WecareGraphItem.WECARE,
        startDestination = WecareGraphItem.AUTHENTICATION
    ) {
        authNavGraph(navHostController = navHostController)
        homeNavGraph(navHostController = navHostController)
        trainingNavGraph(navHostController = navHostController)
        accountNavGraph(navHostController = navHostController)
    }
}

object WecareGraphItem {
    const val WECARE = "WECARE"
    const val AUTHENTICATION = "AUTH_GRAPH"
    const val HOME = "HOME_GRAPH"
    const val TRAINING = "TRAINING_GRAPH"
    const val ACCOUNT = "ACCOUNT_GRAPH"
}