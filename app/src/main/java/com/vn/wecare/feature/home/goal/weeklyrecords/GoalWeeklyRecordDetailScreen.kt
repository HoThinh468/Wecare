package com.vn.wecare.feature.home.goal.weeklyrecords

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GoalWeeklyRecordDetailScreen(
    modifier: Modifier = Modifier, navigateBack: () -> Unit, viewModel: WeeklyRecordViewModel
) {

    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            WeeklyRecordAppBar(modifier = modifier, navigateBack = navigateBack, uiState = uiState)
        },
    ) {

    }
}