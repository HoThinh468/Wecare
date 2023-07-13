package com.vn.wecare.feature.home.goal.dashboard.goaldetail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.vn.wecare.R
import com.vn.wecare.feature.home.goal.dashboard.GoalDetailUiState
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun GoalRecommendation(
    modifier: Modifier, detailUi: GoalDetailUiState
) {
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
            index = "${detailUi.caloriesRecommend} cal",
            description = "Calories burnt",
            iconRes = R.drawable.ic_fire_calo
        )
        RecommendationIndexItem(
            modifier = modifier,
            color = MaterialTheme.colors.primary,
            index = "${detailUi.stepRecommend} steps",
            description = "Steps count",
            iconRes = R.drawable.ic_step
        )
        RecommendationIndexItem(
            modifier = modifier,
            color = Blue,
            index = "${detailUi.activeTimeRecommend} min",
            description = "Active time",
            iconRes = R.drawable.ic_time_clock
        )
    }
}

@Composable
fun RecommendationIndexItem(
    modifier: Modifier, color: Color, index: String, description: String, @DrawableRes iconRes: Int
) {
    Column(
        modifier = modifier.widthIn(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = description, style = MaterialTheme.typography.body2)
        Icon(
            painter = painterResource(id = iconRes), tint = color, contentDescription = null
        )
        Text(text = index, style = MaterialTheme.typography.h5)
    }
}