package com.vn.wecare.feature.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.utils.common_composable.LoadingDialog

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel,
    moveToOnboardingScreen: () -> Unit,
    resetStartDestination: () -> Unit,
    moveToHomeScreen: () -> Unit
) {
    val splashUiState = viewModel.splashUiState.collectAsState()

    splashUiState.value.saveUserRes.let {
        when (it) {
            is Response.Loading ->{
                LoadingDialog(loading = it == Response.Loading) {}
            }
            is Response.Success -> {
                Log.d(SplashFragment.splashFlowTag, "Data present in local db")
                if (viewModel.shouldMoveToOnboarding) {
                    moveToOnboardingScreen()
                }
                if (viewModel.shouldMoveToHomeScreen) {
                    resetStartDestination()
                    moveToHomeScreen()
                } else { /* Do nothing */
                }
            }

            is Response.Error -> {
                Log.d(SplashFragment.splashFlowTag, "Fail to get data from Room")
            }

            else -> {/* do nothing */
            }
        }
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