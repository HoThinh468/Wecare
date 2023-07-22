package com.vn.wecare.feature.home.goal.dashboard.goaldetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.DoNotStep
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.vn.wecare.R
import com.vn.wecare.feature.home.goal.dashboard.GoalDetailUiState
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Pink
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.YellowStar
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun GoalMetric(
    modifier: Modifier, detailUi: GoalDetailUiState
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.small,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(midPadding)
        ) {
            GoalDetailHeadlineItem(
                modifier = modifier,
                icon = Icons.Default.Reviews,
                iconColor = MaterialTheme.colors.primary,
                headline = "Goal metrics"
            )
            Spacer(modifier = modifier.height(smallPadding))
            Text(
                text = "Our recommendations just for you",
                style = MaterialTheme.typography.body2.copy(color = colorResource(id = R.color.Black450))
            )
            Spacer(modifier = modifier.height(normalPadding))
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GoalMetricIndexItem(
                    modifier = modifier,
                    color = YellowStar,
                    index = "${detailUi.weightToLose} kg",
                    description = "Weight to lose",
                    icon = Icons.Default.Scale
                )
                GoalMetricIndexItem(
                    modifier = modifier,
                    color = Pink,
                    index = "${detailUi.timeToReachGoal} weeks",
                    description = "Time",
                    icon = Icons.Default.Timelapse
                )
                GoalMetricIndexItem(
                    modifier = modifier,
                    color = Yellow,
                    index = "${detailUi.weeklyGoalWeight} kg",
                    description = "Weekly goal",
                    icon = Icons.Default.Flag
                )
            }
            Spacer(modifier = modifier.height(midPadding))
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GoalMetricIndexItem(
                    modifier = modifier,
                    color = MaterialTheme.colors.primary,
                    index = "${detailUi.caloriesInGoal} cal",
                    description = "Calories in/day",
                    icon = Icons.Default.ArrowUpward
                )
                GoalMetricIndexItem(
                    modifier = modifier,
                    color = Red400,
                    index = "${detailUi.caloriesOutGoal} cal",
                    description = "Calories out/day",
                    icon = Icons.Default.ArrowDownward
                )
            }
            Spacer(modifier = modifier.height(midPadding))
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GoalMetricIndexItem(
                    modifier = modifier,
                    color = Red400,
                    index = "${detailUi.caloriesRecommend} cal",
                    description = "Calories",
                    icon = Icons.Default.LocalFireDepartment
                )
                Column(modifier = modifier.widthIn()) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_step),
                        tint = MaterialTheme.colors.primary,
                        contentDescription = null
                    )
                    Text(text = "Steps", style = MaterialTheme.typography.body2)
                    Text(text = "${detailUi.stepRecommend}", style = MaterialTheme.typography.h4)
                }
                GoalMetricIndexItem(
                    modifier = modifier,
                    color = Blue,
                    index = "${detailUi.activeTimeRecommend} min",
                    description = "Move time",
                    icon = Icons.Default.Timer
                )
            }
        }
    }
}

@Composable
fun GoalMetricIndexItem(
    modifier: Modifier, color: Color, index: String, description: String, icon: ImageVector
) {
    Column(modifier = modifier.widthIn()) {
        Icon(
            imageVector = icon, tint = color, contentDescription = null
        )
        Text(text = description, style = MaterialTheme.typography.body2)
        Text(text = index, style = MaterialTheme.typography.h4)
    }
}