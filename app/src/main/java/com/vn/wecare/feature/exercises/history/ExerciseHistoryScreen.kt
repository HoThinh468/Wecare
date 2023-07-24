package com.vn.wecare.feature.exercises.history

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.vn.wecare.R
import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.feature.exercises.done.workoutTime
import com.vn.wecare.feature.exercises.exercise_list.TextWithIcon
import com.vn.wecare.ui.theme.Black30
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey100
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.extraLargePadding
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.tinyPadding
import com.vn.wecare.utils.common_composable.BarChartItem
import com.vn.wecare.utils.formatMonthDay
import com.vn.wecare.utils.getMondayOfTimestampWeek
import com.vn.wecare.utils.getSundayOfTimestampWeek
import com.vn.wecare.utils.getWeekDayFromInt
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Preview
@Composable
fun Preview() {
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
        backgroundColor = Grey100,
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
                backgroundColor = Grey100
            )
        }

    ) { values ->
        Column(
            modifier = modifier.padding(values)
        ) {
            Box(modifier = modifier.weight(1.5f)) {
                HistoryChartReport(
                    modifier = modifier,
                    viewModel = viewModel
                )
            }
            Box(modifier = modifier.weight(1f)) {
                Column(
                    modifier = modifier.padding(end = halfMidPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var listHistory =
                        viewModel.listHistoryDisplay.collectAsState().value ?: listOf()
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
private fun HistoryChartReport(
    modifier: Modifier = Modifier, viewModel: ExerciseHistoryViewModel
) {

    val listChart = viewModel.listChartDisplay.collectAsState().value

    Card(
        modifier = modifier
            .padding(horizontal = midPadding, vertical = smallPadding)
            .fillMaxWidth()
            .clip(RoundedCornerShape(mediumRadius))
            .background(MaterialTheme.colors.background), elevation = 20.dp
    ) {
        Column(
            modifier = modifier.padding(horizontal = smallPadding, vertical = smallPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "${
                        formatMonthDay(getMondayOfTimestampWeek(viewModel.historyViewTime.collectAsState().value))
                    } - ${
                        formatMonthDay(getSundayOfTimestampWeek(viewModel.historyViewTime.collectAsState().value))
                    }",
                    style = WeCareTypography.h4,
                    modifier = modifier.padding(start = halfMidPadding, top = tinyPadding)
                )
                Row {
                    IconButton(onClick = { viewModel.decreaseViewTime() }) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = null,
                            tint = Green500
                        )
                    }
                    IconButton(enabled = viewModel.isNextBtnEnable.collectAsState().value,
                        onClick = { viewModel.increaseViewTime() }) {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null,
                            tint = if (viewModel.isNextBtnEnable.collectAsState().value) Green500 else Grey500
                        )
                    }
                }
            }
            Spacer(modifier = modifier.height(midPadding))
            if (!listChart.isNullOrEmpty()) {
                Row(
                    modifier = modifier
                        .weight(9f)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for ((index, item) in listChart.withIndex()) {
                        val progress = item / 600f
                        BarChartItem(
                            itemTitle = getWeekDayFromInt(index + 1),
                            progress = progress,
                            index = item.toInt()
                        )
                    }
                }
            } else {
                val imageLoader = ImageLoader.Builder(LocalContext.current)
                    .components {
                        if (Build.VERSION.SDK_INT >= 28) {
                            add(ImageDecoderDecoder.Factory())
                        } else {
                            add(GifDecoder.Factory())
                        }
                    }
                    .build()
                Image(
                    modifier = modifier
                        .weight(9f),
                    painter = rememberAsyncImagePainter(
                        R.drawable.no_infor, imageLoader
                    ),
                    contentDescription = "",
                )
                Text(
                    text = "No report for this week!",
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = modifier.height(halfMidPadding))
            Row(
                modifier = modifier.weight(2f), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = modifier
                        .size(40.dp)
                        .weight(1f),
                    painter = painterResource(id = R.drawable.kcal),
                    contentDescription = null
                )
                Column(
                    modifier = modifier
                        .weight(6f)
                        .padding(horizontal = halfMidPadding)
                ) {
                    val kcal = listChart.sumOf { it.toDouble() }.toFloat().div(7f)
                    Text(text = "Average", style = MaterialTheme.typography.body2)
                    Text(
                        text = "${
                            DecimalFormat("#.##").format(
                                kcal ?: 0f
                            )
                        } cal",
                        style = MaterialTheme.typography.body1
                    )
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
            .padding(vertical = halfMidPadding)
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .weight(3f)
                .padding(start = midPadding),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = exerciseTitle(dateTime), style = WeCareTypography.body1
            )
            Text(
                text = "$exercisesCount exercises",
                style = WeCareTypography.body2,
                color = Black30
            )
        }
        Column(
            modifier = modifier.weight(1.5f)
        ) {
            TextWithIcon(
                icon = Icons.Outlined.Timer,
                tint = Black30,
                text = workoutTime(duration)
            )
            TextWithIcon(
                icon = Icons.Default.Bolt,
                tint = Black30,
                text = "${DecimalFormat("#.##").format(kcal)} cal"
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
            .padding(bottom = smallPadding)
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .weight(3f)
                .padding(start = extraLargePadding),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = exerciseType.toString(), style = WeCareTypography.body1
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
                text = "${DecimalFormat("#.##").format(kcal)} cal"
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
