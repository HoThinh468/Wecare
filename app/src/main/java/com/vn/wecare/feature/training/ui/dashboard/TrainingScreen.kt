package com.vn.wecare.feature.training.ui.dashboard

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
import com.vn.wecare.feature.training.ui.dashboard.widget.CheckingWeeklySummarySection
import com.vn.wecare.feature.training.ui.dashboard.widget.HistoryTrainingSection
import com.vn.wecare.feature.training.ui.dashboard.widget.StartingATrainingSection
import com.vn.wecare.ui.theme.Grey20

@Preview
@Composable
fun TrainingScreen(
    modifier: Modifier = Modifier,
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
                StartingATrainingSection(modifier)
                HistoryTrainingSection(modifier = modifier)
            }
        },
        backgroundColor = Grey20
    )
}

@Composable
fun TopBar(
    text: String,
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
            IconButton(onClick = {}) {
                Icon(Icons.Default.ArrowBack, "back icon", tint = MaterialTheme.colors.primary)
            }
        },
        actions = {
            if(firstActionIcon != null && firstAction != {} ) IconButton(onClick = firstAction) {
                Icon(firstActionIcon, "first icon", tint = MaterialTheme.colors.primary)
            }
            if(secondActionIcon != null) IconButton(onClick = secondAction) {
                Icon(secondActionIcon, "second icon", tint = MaterialTheme.colors.primary)
            }
        }
    )
}

