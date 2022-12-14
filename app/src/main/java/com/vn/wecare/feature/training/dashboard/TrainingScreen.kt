package com.vn.wecare.feature.training.dashboard

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.vn.wecare.ui.theme.Grey20
import com.vn.wecare.feature.training.dashboard.widget.CheckingWeeklySummarySection
import com.vn.wecare.feature.training.dashboard.history.ui.HistoryTrainingSection
import com.vn.wecare.feature.training.dashboard.widget.StartingATrainingSection
import com.vn.wecare.feature.training.utils.UserAction
import com.vn.wecare.utils.common_composable.RequestPermission

@Composable
fun TrainingScreen(
    modifier: Modifier = Modifier,
    moveToWalkingScreen: (UserAction) -> Unit,
    navigateBack: () -> Unit,
    moveToRunningScreen: (UserAction) -> Unit,
    moveToCyclingScreen: (UserAction) -> Unit,
    moveToMeditationScreen: (UserAction) -> Unit,
) {
    RequestPermission(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    Scaffold(
        topBar = {
            TopBar(
                text = "Training Section",
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CheckingWeeklySummarySection(
                    modifier,
                    duration = 200,
                    kcal = 23.1,
                    session = 7
                )
                StartingATrainingSection(modifier,
                    moveToWalkingScreen,
                    moveToRunningScreen,
                    moveToCyclingScreen,
                    moveToMeditationScreen)
                HistoryTrainingSection(modifier = modifier)
            }
        },
        backgroundColor = Grey20
    )
}

@Composable
fun TopBar(
    text: String,
    navigateBack: () -> Unit = {},
    firstActionIcon: ImageVector? = null,
    firstAction: () -> Unit = {},
    secondActionIcon: ImageVector? = null,
    secondAction: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(text = text, color = MaterialTheme.colors.primary)
        },
        backgroundColor = Color.White,
        navigationIcon = {
            if(navigateBack != {}) IconButton(onClick = navigateBack) {
                Icon(Icons.Default.ArrowBack, "back icon", tint = MaterialTheme.colors.primary)
            }
        },
        actions = {
            if(firstActionIcon != null && firstAction != {} ) IconButton(onClick = firstAction) {
                Icon(firstActionIcon, "first action", tint = MaterialTheme.colors.primary)
            }
            if(secondActionIcon != null && secondAction != {}) IconButton(onClick = secondAction) {
                Icon(secondActionIcon, "second action", tint = MaterialTheme.colors.primary)
            }
        }
    )
}

