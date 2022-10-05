package com.vn.wecare.core_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.vn.wecare.WecareApp
import com.vn.wecare.feature.authentication.authNavGraph

@Composable
fun RootNavigationGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = GraphRootItem.ROOT,
        startDestination = GraphRootItem.AUTHENTICATION
    ) {
        /** The root graph contains two part:
         * - A small graph for authentication section
         * - A composable function that contain content of wecare app with a scaffold
         */
        authNavGraph(navHostController =  navHostController)
        composable(route = GraphRootItem.WECAREAPP) {
            WecareApp()
        }
    }
}

object GraphRootItem {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val WECAREAPP = "wecare_app_graph"
}