package com.vn.wecare.feature.home.goal.weeklyrecords

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.midPadding

@Composable
fun WeeklyGoalSummary(
    modifier: Modifier,
    totalCaloriesIn: Int,
    totalCaloriesOutWithBMR: Int,
    goalName: String,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.small,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(midPadding)
        ) {
            Text(
                text = "Summary",
                style = MaterialTheme.typography.h4,
                modifier = modifier.fillMaxWidth()
            )
            ReportDetailItem(
                modifier = modifier,
                title = "Total calories in",
                index = "$totalCaloriesIn cal",
            )
            ReportDetailItem(
                modifier = modifier,
                title = "Total calories out with BMR",
                index = "$totalCaloriesOutWithBMR cal",
            )
            ReportDetailItem(
                modifier = modifier,
                title = if (goalName == EnumGoal.LOSEWEIGHT.value) "Calories out - calories in" else "Calories in - calories out",
                index = "",
                hasDivider = false
            )
            ReportDetailItem(
                modifier = modifier,
                title = "",
                index = if (goalName == EnumGoal.LOSEWEIGHT.value) "${totalCaloriesOutWithBMR - totalCaloriesIn}" else "${totalCaloriesIn - totalCaloriesOutWithBMR}",
            )
        }
    }
}