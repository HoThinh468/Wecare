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
        route = WECARE,
        startDestination = AuthenticationRoutes.graph
    ) {
        authNavGraph(navHostController = navHostController)
        homeNavGraph(navHostController = navHostController)
        trainingNavGraph(navHostController = navHostController)
        accountNavGraph(navHostController = navHostController)
    }
}