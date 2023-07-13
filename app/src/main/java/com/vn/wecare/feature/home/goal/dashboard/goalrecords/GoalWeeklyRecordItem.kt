package com.vn.wecare.feature.home.goal.dashboard.goalrecords

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat2
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.roundedCornerShape

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalWeeklyRecordItem(
    modifier: Modifier,
    onItemClick: (record: GoalWeeklyRecord) -> Unit,
    index: Int,
    record: GoalWeeklyRecord
) {

    val isEnabled = System.currentTimeMillis() > record.startDate

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = midPadding),
        shape = roundedCornerShape,
        enabled = isEnabled,
        onClick = {
            onItemClick(record)
        },
        border = BorderStroke(
            2.dp,
            color = if (isEnabled) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
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
                    .padding(end = midPadding),
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
                        .clip(CircleShape)
                        .size(40.dp)
                        .background(MaterialTheme.colors.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        index.toString(),
                        style = MaterialTheme.typography.h2.copy(MaterialTheme.colors.onPrimary),
                    )
                }
            }
            Spacer(modifier = modifier.height(halfMidPadding))
            Divider()
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = mediumPadding)
                    .height(IntrinsicSize.Min), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "${record.caloriesIn} cal", style = MaterialTheme.typography.h5)
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
                    Text(text = "${record.caloriesOut} cal", style = MaterialTheme.typography.h5)
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
        }
    }
}