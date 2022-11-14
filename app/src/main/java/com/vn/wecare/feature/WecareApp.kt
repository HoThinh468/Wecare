package com.vn.wecare.feature

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vn.wecare.core.navigation.NavigationBarScreen
import com.vn.wecare.core.navigation.WecareNavGraph

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalMaterialApi
@Composable
fun WecareApp(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        // Add wecare nav graph which contains nav host
        WecareNavGraph(navHostController = navController)
    }
}

private val items = listOf(
    NavigationBarScreen.Home,
    NavigationBarScreen.Training,
    NavigationBarScreen.Account
)

@Composable
fun getCurrentDestination(navController: NavHostController): NavDestination? {
    // Get the current back stack entry using navController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // Get the current destination (which is on top and visible to users)
    return navBackStackEntry?.destination
}

@Composable
fun isMainTab(navController: NavHostController): Boolean {
    // Check if current Destination is one of the four main tab
    return items.any { it.route == getCurrentDestination(navController = navController)?.route }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // If current destination is in the main tabs then show the bottom nav bar
    if (isMainTab(navController)) {
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        ) {
            items.forEach {
                AddItem(
                    item = it,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    item: NavigationBarScreen,
    navController: NavHostController
) {
    val isCurrentTabSelected =
        getCurrentDestination(navController = navController)?.hierarchy?.any {
            it.route == item.route
        } == true

    BottomNavigationItem(
        label = {
            Text(
                text = stringResource(id = item.title),
                style = MaterialTheme.typography.caption
            )
        },
        alwaysShowLabel = true,
        icon = {
            Icon(
                painter = painterResource(
                    id = if (isCurrentTabSelected) item.selectedIcon else item.icon
                ),
                contentDescription = null
            )
        },
        selected = isCurrentTabSelected,
        unselectedContentColor = MaterialTheme.colors.secondary,
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
    )
}