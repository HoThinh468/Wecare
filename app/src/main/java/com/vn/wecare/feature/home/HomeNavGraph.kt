package com.vn.wecare.feature.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core.navigation.HomeRoutes
import com.vn.wecare.feature.home.step_count.ui.compose.SetYourGoalScreen

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalMaterialApi
fun NavGraphBuilder.homeNavGraph(navHostController: NavHostController) {
    navigation(
        route = HomeRoutes.graph,
        startDestination = HomeRoutes.homeDes
    ) {
        composable(route = HomeRoutes.SetYourGoalDes) {
            SetYourGoalScreen {
                navHostController.navigateUp()
            }
        }
    }
}