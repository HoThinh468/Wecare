package com.vn.wecare.feature.account

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core_navigation.WecareAppGraphItem
import com.vn.wecare.feature.AccountOverviewScreen

fun NavGraphBuilder.accountNavGraph(navHostController: NavHostController) {
    navigation(
        route = WecareAppGraphItem.ACCOUNT,
        startDestination = AccountSection.AccountOverview.route
    ) {
        composable(route = AccountSection.AccountOverview.route) {
            AccountOverviewScreen()
        }
    }
}

sealed class AccountSection(val route: String) {
    object AccountOverview : AccountSection(route = "ACCOUNT_OVERVIEW")
}