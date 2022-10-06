package com.vn.wecare.feature

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vn.wecare.core_navigation.WecareNavGraph
import com.vn.wecare.core_navigation.NavigationBarScreen

@Composable
fun WecareApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        // Add wecare nav graph which contains nav host
        WecareNavGraph(navHostController = navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationBarScreen.Home,
        NavigationBarScreen.Training,
        NavigationBarScreen.Account
    )

    // Get the current back stack entry using navController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Get the current destination (which is on top and visible to users)
    val currentDestination = navBackStackEntry?.destination

    // Check if current Destination is one of the four main tab
    val isMainTab = items.any { it.route == currentDestination?.route }

    // If current destination is in the main tabs then show the bottom nav bar
    if (isMainTab) {
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        ) {
            items.forEach {
                AddItem(
                    item = it,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    item: NavigationBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = { Text(text = item.title) },
        alwaysShowLabel = true,
        icon = {
            Icon(
                imageVector = item.icon,
                contentDescription = "Navigation Icon"
            )
        },
        //? What does this mean
        selected = currentDestination?.hierarchy?.any {
            it.route == item.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
    )
}