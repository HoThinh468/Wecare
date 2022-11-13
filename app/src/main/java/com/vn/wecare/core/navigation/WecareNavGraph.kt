package com.vn.wecare.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vn.wecare.feature.account.accountNavGraph
import com.vn.wecare.feature.authentication.authNavGraph
import com.vn.wecare.feature.home.homeNavGraph
import com.vn.wecare.feature.training.trainingNavGraph

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalMaterialApi
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