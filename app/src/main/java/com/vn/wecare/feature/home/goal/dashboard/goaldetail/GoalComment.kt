package com.vn.wecare.feature.home.goal.dashboard.goaldetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Pink
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.mediumIconSize
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxxExtraPadding

@Composable
fun GoalComment(modifier: Modifier) {
    GoalDetailHeadlineItem(
        modifier = modifier,
        icon = Icons.Default.Comment,
        iconColor = Pink,
        headline = "Our comment"
    )
    Spacer(modifier = modifier.height(smallPadding))
    Text(
        text = "You are doing very well, keep on going and don't forget to check your progress regularly!",
        style = MaterialTheme.typography.body2.copy(color = colorResource(id = R.color.Black450))
    )
    Spacer(modifier = modifier.height(smallPadding))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = xxxExtraPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = modifier.size(mediumIconSize),
            imageVector = Icons.Default.SentimentDissatisfied,
            contentDescription = null,
            tint = Red400
        )
        Icon(
            modifier = modifier.size(mediumIconSize),
            imageVector = Icons.Default.SentimentSatisfied,
            contentDescription = null,
            tint = Blue
        )
        Icon(
            modifier = modifier.size(mediumIconSize),
            imageVector = Icons.Default.SentimentVerySatisfied,
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
    }
}