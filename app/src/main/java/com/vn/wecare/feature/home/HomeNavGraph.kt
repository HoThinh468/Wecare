package com.vn.wecare.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core_navigation.NavigationBarScreen
import com.vn.wecare.core_navigation.WecareGraphItem

fun NavGraphBuilder.homeNavGraph(navHostController: NavHostController) {
    navigation(
        route = WecareGraphItem.HOME,
        startDestination = NavigationBarScreen.Home.route
    ) {
        composable(route = NavigationBarScreen.Home.route) {
            HomeScreen()
        }
    }
}