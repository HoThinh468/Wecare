package com.vn.wecare.feature.training

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core.navigation.TrainingRoutes
import com.vn.wecare.feature.training.ui.walking.WalkingScreen

fun NavGraphBuilder.trainingNavGraph(navHostController: NavHostController) {
    navigation(
        route = TrainingRoutes.graph,
        startDestination = TrainingRoutes.trainingDes
    ) {
        composable(route = TrainingRoutes.trainingDes) {
            WalkingScreen()
        }
    }
}