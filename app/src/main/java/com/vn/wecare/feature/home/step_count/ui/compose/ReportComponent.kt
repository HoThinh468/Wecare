package com.vn.wecare.feature.home.step_count.ui.compose

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.vn.wecare.R
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.getStepsFromCaloriesBurned
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.midRadius
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.smallRadius
import com.vn.wecare.ui.theme.tinyPadding
import com.vn.wecare.utils.common_composable.BarChartItem
import com.vn.wecare.utils.formatMonthDay
import com.vn.wecare.utils.getMondayOfTimestampWeek
import com.vn.wecare.utils.getSundayOfTimestampWeek
import com.vn.wecare.utils.getWeekDayFromInt
import java.text.DecimalFormat

@Composable
fun StepsCountReport(
    modifier: Modifier = Modifier, viewModel: StepCountViewModel
) {

    viewModel.loadListHistory()

    val listChart = viewModel.listChartDisplay.collectAsState().value
    val goal = LatestGoalSingletonObject.getInStance()
    val targetCalo = goal.caloriesBurnedGoalForStepCount
    val info = WecareUserSingletonObject.getInstance()
    val targetSteps = targetCalo.getStepsFromCaloriesBurned(info.height ?: 1, info.weight ?: 1)

    Card(
        border = BorderStroke(1.dp, Grey500),
        shape = Shapes.small,
        modifier = modifier
            .padding(vertical = tinyPadding)
            .height(400.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(smallRadius))
            .background(MaterialTheme.colors.background),
        elevation = 20.dp
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
                        val progress = item / targetSteps.toFloat()
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
                    painter = painterResource(id = R.drawable.footstep),
                    contentDescription = null
                )
                Column(
                    modifier = modifier
                        .weight(6f)
                        .padding(horizontal = halfMidPadding)
                ) {
                    val steps = listChart.sumOf { it.toDouble() }.toFloat().div(7f)
                    Text(text = "Average", style = MaterialTheme.typography.body2)
                    Text(
                        text = "${steps.toInt()} steps",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}