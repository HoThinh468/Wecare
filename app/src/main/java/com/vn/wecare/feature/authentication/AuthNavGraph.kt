package com.vn.wecare.feature.authentication

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core_navigation.WecareGraphItem

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController) {
    navigation(
        route = WecareGraphItem.AUTHENTICATION,
        startDestination = AuthSection.SignIn.route
    ) {
        composable(route = AuthSection.SignIn.route) {
            // Add the SignIn screen content
            SignInScreen(
                onMoveClick = {
                    navHostController.navigate(WecareGraphItem.HOME)
                }
            )
        }
        // TODO: Add 2 more screens (sign up and forgot password) with composable(...)
    }
}

sealed class AuthSection(val route: String) {
    object SignIn : AuthSection(route = "sign_in")
    object SignUp : AuthSection(route = "sign_up")
    object ForgotPass : AuthSection(route = "forgot_password")
}