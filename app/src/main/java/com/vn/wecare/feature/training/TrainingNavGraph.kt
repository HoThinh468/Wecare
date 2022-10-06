package com.vn.wecare.feature.training

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core_navigation.NavigationBarScreen
import com.vn.wecare.core_navigation.WecareGraphItem
import com.vn.wecare.feature.home.HomeScreen

fun NavGraphBuilder.trainingNavGraph(navHostController: NavHostController) {
    navigation(
        route = WecareGraphItem.TRAINING,
        startDestination = NavigationBarScreen.Training.route
    ) {
        composable(route = NavigationBarScreen.Training.route) {
            TrainingScreen()
        }
    }
}