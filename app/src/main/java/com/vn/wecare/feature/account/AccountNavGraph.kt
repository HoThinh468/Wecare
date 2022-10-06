package com.vn.wecare.feature.account

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core_navigation.NavigationBarScreen
import com.vn.wecare.core_navigation.WecareGraphItem

fun NavGraphBuilder.accountNavGraph(navHostController: NavHostController) {
    navigation(
        route = WecareGraphItem.ACCOUNT,
        startDestination = NavigationBarScreen.Account.route
    ) {
        composable(route = NavigationBarScreen.Account.route) {
            AccountScreen()
        }
    }
}