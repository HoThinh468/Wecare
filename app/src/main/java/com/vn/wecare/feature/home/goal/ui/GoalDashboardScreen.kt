package com.vn.wecare.feature.home.goal.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.home.dashboard.main.DailyCalories
import com.vn.wecare.feature.home.dashboard.main.GoalTracking
import com.vn.wecare.feature.home.goal.GoalDashboardViewModel
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.WecareAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalDashboardScreen(
    modifier: Modifier = Modifier, navigateBack: () -> Unit, viewModel: GoalDashboardViewModel
) {

    val dashboardCaloriesUiState = viewModel.dashboardCaloriesUiState.collectAsState().value

    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            WecareAppBar(
                modifier = modifier, onLeadingIconPress = { navigateBack() }, title = "Dashboard"
            )
        },
    ) {
        Column(modifier = modifier.padding(vertical = smallPadding, horizontal = midPadding)) {
            DailyCalories(
                modifier = modifier,
                remainedCalories = dashboardCaloriesUiState.remainedCalories,
                caloriesIn = dashboardCaloriesUiState.caloriesIn,
                caloriesInProgress = dashboardCaloriesUiState.caloriesInProgress,
                caloriesOut = dashboardCaloriesUiState.caloriesOut,
                caloriesOutProgress = dashboardCaloriesUiState.caloriesOutProgress
            )
            Spacer(modifier = modifier.height(normalPadding))
            GoalTracking(modifier = modifier)
        }
    }
}