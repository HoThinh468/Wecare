package com.vn.wecare.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.vn.wecare.R

sealed class NavigationBarScreen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val selectedIcon: Int
) {
    object Home : NavigationBarScreen(
        route = HomeRoutes.homeDes,
        title = R.string.home_title,
        icon = R.drawable.ic_outline_home,
        selectedIcon = R.drawable.ic_home
    )

    object Training : NavigationBarScreen(
        route = TrainingRoutes.trainingDes,
        title = R.string.training_title,
        icon = R.drawable.ic_outline_sports_baseball,
        selectedIcon = R.drawable.ic_sports_baseball
    )

    object Account : NavigationBarScreen(
        route = AccountRoutes.accountDes,
        title = R.string.account_title,
        icon = R.drawable.ic_person_outline,
        selectedIcon = R.drawable.ic_person
    )
}