package com.vn.wecare.feature.training.ui

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
import androidx.compose.ui.tooling.preview.Preview
import com.vn.wecare.feature.training.ui.widget.CheckingWeeklySummarySection
import com.vn.wecare.feature.training.ui.widget.HistoryTrainingSection
import com.vn.wecare.feature.training.ui.widget.StartingATrainingSection
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey100
import com.vn.wecare.ui.theme.Grey20

@Preview
@Composable
fun TrainingScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Scaffold(
            topBar = { TopBar() },
            content = {
                Column() {
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
}

@Composable
fun TopBar(
) {
    TopAppBar(
        title = {
            Text(text = "Training Section", color = Green500)
        },
        backgroundColor = Color.White,
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.ArrowBack, "back icon", tint = Green500)
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.CalendarToday, "calendar", tint = Green500)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Default.Analytics, "statistic", tint = Green500)
            }
        }
    )
}

