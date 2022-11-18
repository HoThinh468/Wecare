package com.vn.wecare.feature.training

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core_navigation.TrainingRoutes
import com.vn.wecare.feature.training.dashboard.ui.TrainingScreen
import com.vn.wecare.feature.training.walking.ui.WalkingFragment
import com.vn.wecare.feature.training.walking.ui.WalkingScreen

fun NavGraphBuilder.trainingNavGraph(navHostController: NavHostController) {
    navigation(
        route = TrainingRoutes.graph,
        startDestination = TrainingRoutes.trainingDes
    ) {
        composable(route = TrainingRoutes.trainingDes) {
            TrainingScreen(
                moveToWalkingScreen = { navHostController.navigate(TrainingRoutes.walkingDes)}
            )
        }
//
//        composable(route = TrainingRoutes.walkingDes) {
//            WalkingFragment()
//        }
    }
}