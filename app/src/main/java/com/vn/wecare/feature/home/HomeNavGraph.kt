package com.vn.wecare.feature.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core_navigation.HomeRoutes
import com.vn.wecare.feature.home.view.HomeScreen
import com.vn.wecare.feature.home.view.step_count.SetYourGoalScreen
import com.vn.wecare.feature.home.view.step_count.StepCountScreen

fun NavGraphBuilder.homeNavGraph(navHostController: NavHostController) {
    navigation(
        route = HomeRoutes.graph,
        startDestination = HomeRoutes.homeDes
    ) {
        composable(route = HomeRoutes.homeDes) {
            HomeScreen(
                onFootStepCountCardClick = { navHostController.navigate(HomeRoutes.StepCountDes) },
                onWaterCardClick = {},
                onBMICardClick = {},
                onWalkingIcClick = {},
                onRunningIcClick = {},
                onBicycleIcClick = {},
                onMeditationIcClick = {}
            )
        }

        /**
         * This part is for Step count*/
        composable(route = HomeRoutes.StepCountDes) {
            StepCountScreen(
                navigateUp = { navHostController.navigateUp() },
                moveToSetGoalScreen = { navHostController.navigate(HomeRoutes.SetYourGoalDes) }
            )
        }
        composable(route = HomeRoutes.SetYourGoalDes) {
            SetYourGoalScreen {
                navHostController.navigateUp()
            }
        }
    }
}