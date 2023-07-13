package com.vn.wecare.feature.home.goal.dashboard.goaldetail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.Pink
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun GoalStatus(
    modifier: Modifier, status: String
) {
    GoalDetailHeadlineItem(
        modifier = modifier, icon = Icons.Default.Notes, iconColor = Pink, headline = "Status"
    )
    Spacer(modifier = modifier.height(smallPadding))
    Text(
        text = "This goal is currently $status",
        style = MaterialTheme.typography.body2.copy(color = colorResource(id = R.color.Black450))
    )
    Spacer(modifier = modifier.height(midPadding))
}