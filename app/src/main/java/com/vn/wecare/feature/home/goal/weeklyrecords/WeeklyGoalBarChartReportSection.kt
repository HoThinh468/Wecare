package com.vn.wecare.feature.home.goal.weeklyrecords

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.ui.theme.Pink
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.iconSize
import com.vn.wecare.ui.theme.largePadding
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK
import com.vn.wecare.utils.common_composable.BarChartItem
import com.vn.wecare.utils.getDayOfWeekInStringWithLong
import com.vn.wecare.utils.getProgressInFloatWithIntInput

@Composable
fun WeeklyGoalBarChartReportSection(
    modifier: Modifier,
    dailyRecordList: List<GoalDailyRecord>,
    record: GoalWeeklyRecord,
    totalCaloriesIn: Int,
    caloriesInProgress: Float,
    totalCaloriesOut: Int,
    caloriesOutProgress: Float
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.small,
    ) {
        Column(
            modifier = modifier.padding(horizontal = midPadding, vertical = midPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = modifier.height(halfMidPadding))
            Box(
                modifier = modifier
                    .height(250.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${record.weeklyCaloriesGoal / NUMBER_OF_DAYS_IN_WEEK}",
                    modifier = modifier.align(Alignment.TopStart),
                    style = MaterialTheme.typography.caption
                )
                Divider(
                    modifier
                        .align(alignment = Alignment.TopCenter)
                        .padding(top = midPadding, start = largePadding)
                )
                Text(
                    text = "${record.weeklyCaloriesGoal / 2 / NUMBER_OF_DAYS_IN_WEEK}",
                    modifier = modifier.align(Alignment.CenterStart),
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "* Unit: cal",
                    modifier = modifier.align(Alignment.TopEnd),
                    style = MaterialTheme.typography.caption
                )
                Divider(
                    modifier
                        .align(alignment = Alignment.Center)
                        .padding(top = midPadding, start = largePadding)
                )
                Row(
                    modifier = modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState())
                        .padding(top = mediumPadding, start = largePadding),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (item in dailyRecordList) {
                        val progress =
                            getProgressInFloatWithIntInput(item.caloriesIn, item.goalDailyCalories)
                        val progressOut = getProgressInFloatWithIntInput(
                            item.caloriesOut, item.goalDailyCaloriesOut
                        )
                        BarChartItem(
                            itemTitle = getDayOfWeekInStringWithLong(item.dayInLong),
                            progress = progress,
                            index = item.caloriesIn,
                            width = 10.dp,
                            barColor = MaterialTheme.colors.primaryVariant,
                            isOneColumn = false,
                            secondBarColor = Pink,
                            secondBarProgress = progressOut,
                            indexForSecondBar = item.caloriesOut,
                            indexColor = MaterialTheme.colors.primary,
                            secondBarIndexColor = Pink
                        )
                    }
                }
            }
            Spacer(modifier = modifier.height(smallPadding))
            Text(
                text = "* Calories out above just contain workout and exercise activity",
                style = MaterialTheme.typography.caption,
                modifier = modifier.fillMaxWidth()
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = midPadding)
                    .height(IntrinsicSize.Min), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = modifier
                            .size(iconSize)
                            .clip(Shapes.small)
                            .background(MaterialTheme.colors.primary)
                    )
                    Column(modifier = modifier.padding(start = smallPadding)) {
                        Text(text = "Calories in", style = MaterialTheme.typography.body2)
                        Text(
                            text = "$totalCaloriesIn (${(caloriesInProgress * 100).toInt()}%)",
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
                Divider(
                    modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        modifier = modifier.padding(end = smallPadding),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = "Calories out", style = MaterialTheme.typography.body2)
                        Text(
                            text = "$totalCaloriesOut (${(caloriesOutProgress * 100).toInt()}%)",
                            style = MaterialTheme.typography.h4
                        )
                    }
                    Box(
                        modifier = modifier
                            .size(iconSize)
                            .clip(Shapes.small)
                            .background(Pink)
                    )
                }
            }
        }
    }
}

@Composable
fun ReportDetailItem(modifier: Modifier, title: String, index: String, hasDivider: Boolean = true) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = halfMidPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.body2)
            Text(text = index, style = MaterialTheme.typography.body2)
        }
        if (hasDivider) {
            Divider()
        }
    }
}