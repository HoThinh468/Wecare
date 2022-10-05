package com.vn.wecare

import com.vn.wecare.feature.WecareAppNavGraph
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
import com.vn.wecare.feature.navigation.NavigationBarScreen

@Composable
fun WecareApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        // Add wecare nav graph which contains nav host
        WecareGraph(navHostController = navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationBarScreen.Home,
        NavigationBarScreen.Training,
        NavigationBarScreen.Account
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

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

@Composable
fun RowScope.AddItem(
    item: NavigationBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        //? What does this logic mean
        selected = currentDestination?.hierarchy?.any {
            it.route == item.route
        } == true,
        label = { Text(text = item.title) },
        alwaysShowLabel = true,
        icon = {
            Icon(
                imageVector = item.icon,
                contentDescription = "Navigation Icon"
            )
        },
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
    )
}