package com.vn.wecare.feature.training.dashboard.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.vn.wecare.feature.training.dashboard.ui.widget.CheckingWeeklySummarySection
import com.vn.wecare.feature.training.dashboard.ui.widget.HistoryTrainingSection
import com.vn.wecare.feature.training.dashboard.ui.widget.StartingATrainingSection
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey20
import com.vn.wecare.R

@Preview
@Composable
fun TrainingScreenPreview() {
    TrainingScreen(
        modifier = Modifier,
        moveToWalkingScreen = {}
    )
}

@Composable
fun TrainingScreen(
    modifier: Modifier = Modifier,
    moveToWalkingScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                text = "Training Section",
                firstActionIcon = Icons.Default.CalendarToday,
                secondActionIcon = Icons.Default.Analytics
            )
        },
        content = {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CheckingWeeklySummarySection(
                    modifier,
                    duration = 200,
                    kcal = 23.1,
                    session = 7
                )
                StartingATrainingSection(modifier, moveToWalkingScreen)
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

