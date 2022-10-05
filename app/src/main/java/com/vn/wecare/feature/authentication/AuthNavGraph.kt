package com.vn.wecare.feature.authentication

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vn.wecare.core_navigation.GraphRootItem

fun NavGraphBuilder.authNavGraph(navHostController: NavHostController) {
    navigation(
        route = GraphRootItem.AUTHENTICATION,
        startDestination = AuthSection.SignIn.route
    ) {
        composable(route = AuthSection.SignIn.route) {
            // TODO: Add the SignIn screen content
            SignInScreen(
                onHomeScreenClick = {
                    navHostController.navigate(GraphRootItem.WECAREAPP)
                }
            )
        }
        // TODO: Add 2 more screens (sign up and forgot password) with composable(...)
    }
}

sealed class AuthSection(val route: String) {
    object SignIn : AuthSection(route = "SIGNIN")
    object SignUp : AuthSection(route = "SIGNUP")
    object ForgotPass : AuthSection(route = "FORGOTPASS")
}