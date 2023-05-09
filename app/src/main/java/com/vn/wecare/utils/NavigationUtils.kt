package com.vn.wecare.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigate(
    @IdRes startDestinationId: Int, @IdRes actionId: Int
) {
    if (currentDestination?.id == startDestinationId) navigate(actionId)
}

fun NavController.safeNavigateWithBundle(
    @IdRes startDestinationId: Int, @IdRes actionId: Int, bundle: Bundle
) {
    if (currentDestination?.id == startDestinationId) navigate(actionId, bundle)
}