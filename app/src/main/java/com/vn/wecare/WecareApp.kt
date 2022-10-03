package com.vn.wecare

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

enum class WecareScreen {
    SignIn,
    SignUp,
    Home
}

@Composable
fun WecareApp(
    modifier: Modifier = Modifier
) {
    // Create a nav controller
    val navController = rememberNavController()

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    // Get the name of the current screen
    val currentScreen =
        WecareScreen.valueOf(backStackEntry?.destination?.route ?: WecareScreen.Home.name)
}