package com.vn.wecare.feature.exercises.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Timer10
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.feature.exercises.done.workoutTime
import com.vn.wecare.feature.exercises.exercise_list.TextWithIcon
import com.vn.wecare.ui.theme.Black30
import com.vn.wecare.ui.theme.Grey20
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.extraLargePadding
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.utils.formatMonthDay
import com.vn.wecare.utils.getMondayOfTimestampWeek
import com.vn.wecare.utils.getSundayOfTimestampWeek
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Preview
@Composable
fun preview() {
    ExerciseHistoryScreen(
        onNavigationBack = {}
    )
}

@Composable
fun ExerciseHistoryScreen(
    modifier: Modifier = Modifier,
    onNavigationBack: () -> Unit,
    viewModel: ExerciseHistoryViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onNavigationBack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                title = {
                    Text(
                        text = "Exercise History",
                        style = WeCareTypography.h5,
                        color = Color.Black
                    )
                },
                backgroundColor = Color.White
            )
        }

    ) { values ->
        Column(
            modifier = modifier.padding(values)
        ) {
            Box(modifier = modifier.weight(1f))
            Box(modifier = modifier.weight(1f)) {
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var listHistory = viewModel.listHistoryDisplay.collectAsState().value ?: listOf()
                    ExerciseHistoryTitle(
                        dateTime = viewModel.historyViewTime.collectAsState().value,
                        exercisesCount = listHistory.size,
                        kcal = listHistory?.sumOf { it.kcal.toDouble() }?.toFloat() ?: 0f,
                        duration = listHistory.sumOf { it.duration.toLong() * 1000 }
                    )
                    LazyColumn {
                        items(listHistory.reversed()) { history ->
                            ExerciseHistoryItem(
                                exerciseType = history.type,
                                dateTime = history.time,
                                kcal = history.kcal,
                                duration = history.duration
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseHistoryTitle(
    modifier: Modifier = Modifier,
    dateTime: Long,
    exercisesCount: Int,
    kcal: Float,
    duration: Long
) {
    Row(
        modifier = modifier
            .padding(vertical = midPadding)
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .weight(3f)
                .padding(start = midPadding),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = exerciseTitle(dateTime), style = WeCareTypography.h3
            )
            Text(
                text = "$exercisesCount exercises",
                style = WeCareTypography.body1,
                color = Black30
            )
        }
        Column(
            modifier = modifier.weight(1f)
        ) {
            TextWithIcon(
                icon = Icons.Outlined.Timer,
                tint = Black30,
                text = workoutTime(duration)
            )
            TextWithIcon(
                icon = Icons.Default.Bolt,
                tint = Black30,
                text = "${DecimalFormat("#.##").format(kcal)} Kcal"
            )
        }
    }
}

@Composable
fun ExerciseHistoryItem(
    modifier: Modifier = Modifier,
    exerciseType: ExerciseType,
    dateTime: Long,
    kcal: Float,
    duration: Int
) {
    Row(
        modifier = modifier
            .padding(vertical = halfMidPadding)
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .weight(3f)
                .padding(start = extraLargePadding),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = exerciseType.toString(), style = WeCareTypography.h4
            )
            Text(
                text = formatTimestamp(dateTime),
                style = WeCareTypography.body2,
                color = Black30
            )
        }
        Column(
            modifier = modifier.weight(1f)
        ) {
            TextWithIcon(
                icon = Icons.Outlined.Timer,
                tint = Black30,
                text = workoutTime(duration.toLong() * 1000)
            )
            TextWithIcon(
                icon = Icons.Default.Bolt,
                tint = Black30,
                text = "${DecimalFormat("#.##").format(kcal)} Kcal"
            )
        }
    }
}

fun exerciseTitle(dateTime: Long): String {
    return "${
        formatMonthDay(
            getMondayOfTimestampWeek(
                dateTime
            )
        )
    } - ${
        formatMonthDay(
            getSundayOfTimestampWeek(dateTime)
        )
    }"
}

fun formatTimestamp(timestamp: Long): String {
    val instant = Instant.ofEpochMilli(timestamp)
    val zoneId = ZoneId.systemDefault()
    val localDateTime = LocalDateTime.ofInstant(instant, zoneId)
    val formatter = DateTimeFormatter.ofPattern("MMM dd, HH'h'mm")
    return localDateTime.format(formatter)
}
