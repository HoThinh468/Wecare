package com.vn.wecare

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

enum class WecareScreen {
    SignIn,
    SignUp,
}

@Composable
fun WecareApp() {
    // Create a nav controller
    val navController = rememberNavController()

}