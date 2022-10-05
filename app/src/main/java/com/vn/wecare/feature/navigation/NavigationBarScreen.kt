package com.vn.wecare.feature.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : NavigationBarScreen(
        route = "HOME",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Training : NavigationBarScreen(
        route = "TRAINING",
        title = "Training",
        icon = Icons.Default.Share
    )

    object Account : NavigationBarScreen(
        route = "ACCOUNT",
        title = "Account",
        icon = Icons.Default.Person
    )
}
