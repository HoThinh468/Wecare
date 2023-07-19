package com.vn.wecare.feature.home.goal.dashboard.goaldetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.home.goal.dashboard.GoalDetailUiState
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxxExtraPadding

@Composable
fun GoalDetailSection(
    modifier: Modifier, detailUi: GoalDetailUiState
) {
    Column(
        modifier = modifier
            .padding(bottom = xxxExtraPadding)
            .verticalScroll(rememberScrollState())
    ) {
        GoalOverview(modifier = modifier, detailUi = detailUi)
        Spacer(modifier = modifier.height(normalPadding))
        GoalMetric(modifier = modifier, detailUi = detailUi)
        Spacer(modifier = modifier.height(mediumPadding))
    }
}

@Composable
fun GoalDetailHeadlineItem(
    modifier: Modifier, icon: ImageVector, iconColor: Color, headline: String
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = modifier.size(18.dp),
            imageVector = icon,
            contentDescription = null,
            tint = iconColor
        )
        Spacer(modifier = modifier.width(smallPadding))
        Text(text = headline, style = MaterialTheme.typography.h5)
    }
}