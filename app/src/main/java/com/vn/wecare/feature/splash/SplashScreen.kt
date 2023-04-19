package com.vn.wecare.feature.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.vn.wecare.R

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel,
    moveToOnboardingScreen: () -> Unit,
    moveToAuthenticationScreen: () -> Unit,
    resetStartDestination: () -> Unit,
    moveToHomeScreen: () -> Unit
) {
//    val splashUiState = viewModel.splashUiState.collectAsState()

//    splashUiState.value.saveUserRes.let {
//        when (it) {
//            is Response.Loading -> LoadingDialog(loading = it == Response.Loading) {}
//            is Response.Success -> {
//                Log.d(SplashFragment.splashFlowTag, "All data is enough, move to home")
//                resetStartDestination()
//                moveToHomeScreen()
//            }
//            is Response.Error -> {
//                Toast.makeText(LocalContext.current, "Cannot sync user data!", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//            else -> {/* do nothing */
//            }
//        }
//    }

    if (viewModel.shouldMoveToAuthentication) {
        Log.d(SplashFragment.splashFlowTag, "No user, move to authentication")
        moveToAuthenticationScreen()
    } else if (viewModel.shouldMoveToOnboarding) {
        moveToOnboardingScreen()
    } else {
        resetStartDestination()
        moveToHomeScreen()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Image(
            modifier = modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.logo2),
            contentDescription = null
        )
    }
}