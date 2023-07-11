package com.vn.wecare.feature.home.goal.dashboard.goaldetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.goal.dashboard.GoalDetailUiState
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.weightFormat

@Composable
fun GoalCaloriesOverview(modifier: Modifier, detailUi: GoalDetailUiState) {
    GoalDetailHeadlineItem(
        modifier = modifier,
        icon = Icons.Default.LocalFireDepartment,
        iconColor = Red400,
        headline = "Calories overview"
    )
    Spacer(modifier = modifier.height(smallPadding))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = "${detailUi.totalCaloriesIn} cal", style = MaterialTheme.typography.h5)
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
        Column {
            Text(text = "${detailUi.totalCaloriesOut} cal", style = MaterialTheme.typography.h5)
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
        Text(text = "Calories should take in everyday:", style = MaterialTheme.typography.button)
        Text(text = "${detailUi.caloriesInGoal} cal", style = MaterialTheme.typography.button)
    }
    Spacer(modifier = modifier.height(smallPadding))
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Calories to take out everyday:", style = MaterialTheme.typography.button)
        Text(text = "${detailUi.caloriesOutGoal} cal", style = MaterialTheme.typography.button)
    }
    Spacer(modifier = modifier.height(smallPadding))
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Calories difference:", style = MaterialTheme.typography.button)
        Text(text = "${detailUi.caloriesDifference} cal", style = MaterialTheme.typography.button)
    }
    Spacer(modifier = modifier.height(smallPadding))
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Weight difference assumed:", style = MaterialTheme.typography.button)
        Text(
            text = "${detailUi.weightDifference.weightFormat()} kg",
            style = MaterialTheme.typography.button
        )
    }
    Spacer(modifier = modifier.height(smallPadding))
    Text(
        text = "* We assume 1 kg ~ 7700 cal",
        style = MaterialTheme.typography.body2.copy(color = colorResource(id = R.color.Black450))
    )
}