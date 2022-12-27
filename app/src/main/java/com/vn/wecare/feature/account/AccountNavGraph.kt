package com.vn.wecare.feature.account

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core.navigation.AccountRoutes

fun NavGraphBuilder.accountNavGraph(navHostController: NavHostController) {
    navigation(
        route = AccountRoutes.graph,
        startDestination = AccountRoutes.accountDes
    ) {
        composable(route = AccountRoutes.accountDes) {
        }
    }
}