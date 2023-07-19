package com.vn.wecare.feature.home.goal.dashboard.goalrecords

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat2
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMediumIconSize
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.roundedCornerShape
import com.vn.wecare.ui.theme.smallPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalWeeklyRecordItem(
    modifier: Modifier,
    onItemClick: (record: GoalWeeklyRecord) -> Unit,
    index: Int,
    record: GoalWeeklyRecord
) {
    val colorStatus = GoalStatus.getGoalStatusFromValue(record.status).color
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = midPadding),
        shape = roundedCornerShape,
        enabled = record.status != GoalStatus.NOTSTARTED.value,
        onClick = {
            onItemClick(record)
        },
        border = BorderStroke(
            2.dp, color = colorStatus
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(end = normalPadding),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Week $index", style = MaterialTheme.typography.h3)
                    Text(
                        "${getDayFromLongWithFormat2(record.startDate)} - ${
                            getDayFromLongWithFormat2(
                                record.endDate
                            )
                        }", style = MaterialTheme.typography.body2
                    )
                }
                Box(
                    modifier = modifier
                        .clip(Shapes.medium)
                        .background(colorStatus)
                ) {
                    Text(
                        modifier = modifier.padding(vertical = 6.dp, horizontal = smallPadding),
                        text = record.status,
                        style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.onPrimary)
                    )
                }
            }
            Spacer(modifier = modifier.height(smallPadding))
            Divider()
            Spacer(modifier = modifier.height(smallPadding))
            Row(modifier = modifier.padding(start = midPadding)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Timelapse,
                        contentDescription = null,
                        modifier = modifier.size(
                            halfMediumIconSize
                        ),
                    )
                    Spacer(modifier = modifier.width(2.dp))
                    Text(
                        modifier = modifier.padding(vertical = 6.dp, horizontal = smallPadding),
                        text = "${record.numberOfDayRecord} day(s) recorded",
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}