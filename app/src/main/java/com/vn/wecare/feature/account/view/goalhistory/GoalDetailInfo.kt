package com.vn.wecare.feature.account.view.goalhistory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.FlagCircle
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.account.viewmodel.GoalDetailUiState
import com.vn.wecare.feature.home.goal.dashboard.goaldetail.GoalDetailHeadlineItem
import com.vn.wecare.feature.home.goal.dashboard.goaldetail.RecommendationIndexItem
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun GoalDetailInfo(
    modifier: Modifier, goal: Goal, uiState: GoalDetailUiState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = midPadding)
    ) {
        val description = when (goal.goalName) {
            EnumGoal.GAINMUSCLE.value -> "You want to gain ${goal.weightDifference} kg in ${goal.timeToReachGoalInWeek} week(s)"
            EnumGoal.LOSEWEIGHT.value -> "You want to loose ${goal.weightDifference} kg in ${goal.timeToReachGoalInWeek} week(s)"
            else -> "${goal.goalName} in ${goal.timeToReachGoalInWeek} week(s)"
        }
        GoalDetailHeadlineItem(
            modifier = modifier,
            icon = Icons.Default.FlagCircle,
            iconColor = MaterialTheme.colors.primary,
            headline = "Your goal is ${goal.goalName.lowercase()}"
        )
        Spacer(modifier = modifier.height(smallPadding))
        Text(
            text = description,
            style = MaterialTheme.typography.body2.copy(color = colorResource(id = R.color.Black450))
        )
        Spacer(modifier = modifier.height(normalPadding))
        GoalDetailHeadlineItem(
            modifier = modifier,
            icon = Icons.Default.LocalFireDepartment,
            iconColor = Red400,
            headline = "Calories overview:"
        )
        Spacer(modifier = modifier.height(smallPadding))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "${uiState.totalCaloIn} cal", style = MaterialTheme.typography.h5)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropUp,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                    Text(
                        text = "Calories in",
                        style = MaterialTheme.typography.body2.copy(color = colorResource(id = R.color.Black450))
                    )
                }
            }
            Divider(
                modifier = modifier
                    .fillMaxHeight()
                    .padding(horizontal = midPadding)
                    .width(1.dp)
            )
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "${uiState.totalCaloOut} cal", style = MaterialTheme.typography.h5)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Red400
                    )
                    Text(
                        text = "Calories out",
                        style = MaterialTheme.typography.caption.copy(color = colorResource(id = R.color.Black450))
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(halfMidPadding))
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Calories should take in everyday:", style = MaterialTheme.typography.button
            )
            Text(
                text = "${goal.caloriesInEachDayGoal} cal", style = MaterialTheme.typography.button
            )
        }
        Spacer(modifier = modifier.height(smallPadding))
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Calories to take out everyday:", style = MaterialTheme.typography.button)
            Text(
                text = "${goal.caloriesBurnedEachDayGoal} cal",
                style = MaterialTheme.typography.button
            )
        }
        Spacer(modifier = modifier.height(normalPadding))
        GoalDetailHeadlineItem(
            modifier = modifier,
            icon = Icons.Default.Reviews,
            iconColor = Yellow,
            headline = "Our recommendations"
        )
        Spacer(modifier = modifier.height(smallPadding))
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            RecommendationIndexItem(
                modifier = modifier,
                color = Red400,
                index = "${uiState.caloriesRecommendation} cal",
                description = "Calories burnt",
                iconRes = R.drawable.ic_fire_calo
            )
            RecommendationIndexItem(
                modifier = modifier,
                color = MaterialTheme.colors.primary,
                index = "${goal.stepsGoal} steps",
                description = "Steps count",
                iconRes = R.drawable.ic_step
            )
            RecommendationIndexItem(
                modifier = modifier,
                color = Blue,
                index = "${goal.moveTimeGoal} min",
                description = "Active time",
                iconRes = R.drawable.ic_time_clock
            )
        }
    }
}