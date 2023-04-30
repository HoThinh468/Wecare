package com.vn.wecare.utils

import androidx.annotation.IdRes
import androidx.navigation.NavController

fun NavController.safeNavigate(
    @IdRes startDestinationId: Int, @IdRes actionId: Int
) {
    if (currentDestination?.id == startDestinationId) navigate(actionId)
}