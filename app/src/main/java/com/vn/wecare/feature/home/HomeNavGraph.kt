package com.vn.wecare.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core_navigation.HomeRoutes
import com.vn.wecare.feature.home.view.HomeScreen

fun NavGraphBuilder.homeNavGraph(navHostController: NavHostController) {
    navigation(
        route = HomeRoutes.graph,
        startDestination = HomeRoutes.homeDes
    ) {
        composable(route = HomeRoutes.homeDes) {
            HomeScreen()
        }
    }
}