package com.vn.wecare.feature.authentication

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core_navigation.AuthenticationRoutes
import com.vn.wecare.core_navigation.HomeRoutes
import com.vn.wecare.feature.authentication.ui.SignInScreen

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController) {
    navigation(
        route = AuthenticationRoutes.graph,
        startDestination = AuthenticationRoutes.signInDes
    ) {
        composable(route = AuthenticationRoutes.signInDes) {
            // Add the SignIn screen content
            SignInScreen(
                navigateToHome = {
                    navHostController.navigate(HomeRoutes.graph)
                }
            )
        }
        // TODO: Add 2 more screens (sign up and forgot password) with composable(...)
    }
}