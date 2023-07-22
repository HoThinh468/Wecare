package com.vn.wecare.feature.home.goal.weeklyrecords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.food.addmeal.ui.NutrientIndexItem
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK

@Composable
fun WeeklyRecordDetailIndex(
    modifier: Modifier, record: GoalWeeklyRecord, averageCaloriesIn: Int, averageCaloriesOut: Int
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.small,
    ) {
        Column(
            modifier = modifier.padding(horizontal = midPadding, vertical = midPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Total nutrients index",
                style = MaterialTheme.typography.h4,
                modifier = modifier.fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = normalPadding)
            ) {
                NutrientIndexItem(
                    modifier = modifier,
                    color = Yellow,
                    title = "Fat",
                    index = "${record.fatAmount}g"
                )
                NutrientIndexItem(
                    modifier = modifier,
                    color = Red400,
                    title = "Protein",
                    index = "${record.proteinAmount}g"
                )
                NutrientIndexItem(
                    modifier = modifier,
                    color = Blue,
                    title = "Carbs",
                    index = "${record.carbsAmount}g"
                )
            }
            Text(
                text = "Average each day",
                style = MaterialTheme.typography.h4,
                modifier = modifier.fillMaxWidth()
            )
            ReportDetailItem(
                modifier = modifier, title = "Calories in", index = "$averageCaloriesIn cal"
            )
            ReportDetailItem(
                modifier = modifier,
                title = "Calories out",
                index = "$averageCaloriesOut cal",
                hasDivider = false
            )
            Spacer(modifier = modifier.height(halfMidPadding))
            Text(
                text = "Daily goal",
                style = MaterialTheme.typography.h4,
                modifier = modifier.fillMaxWidth()
            )
            ReportDetailItem(
                modifier = modifier,
                title = "Calories in",
                index = "${record.weeklyCaloriesGoal / NUMBER_OF_DAYS_IN_WEEK} cal"
            )
            ReportDetailItem(
                modifier = modifier,
                title = "Calories out",
                index = "${record.weeklyCaloriesOutGoal / NUMBER_OF_DAYS_IN_WEEK} cal",
                hasDivider = false
            )
        }
    }
}