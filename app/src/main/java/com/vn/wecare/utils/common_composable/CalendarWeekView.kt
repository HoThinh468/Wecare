package com.vn.wecare.utils.common_composable

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun CalendarWeekView(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background)
            .padding(vertical = smallPadding, horizontal = normalPadding),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CalendarWeekViewItem(modifier = modifier, day = R.string.sunday, dayValue = 8)
        CalendarWeekViewItem(modifier = modifier, day = R.string.monday, dayValue = 9)
        CalendarWeekViewItem(modifier = modifier, day = R.string.tuesday, dayValue = 10)
        CalendarWeekViewItem(modifier = modifier, day = R.string.wednesday, dayValue = 11)
        CalendarWeekViewItem(
            modifier = modifier,
            day = R.string.thursday,
            dayValue = 12,
            isSelected = true
        )
        CalendarWeekViewItem(modifier = modifier, day = R.string.friday, dayValue = 13)
        CalendarWeekViewItem(modifier = modifier, day = R.string.saturday, dayValue = 14)
    }
}

@Composable
fun CalendarWeekViewItem(
    modifier: Modifier,
    @StringRes day: Int,
    dayValue: Int,
    isSelected: Boolean = false
) {
    Column(
        modifier = modifier
            .heightIn()
            .widthIn(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(id = day),
            style = MaterialTheme.typography.button.copy(color = colorResource(id = R.color.Black450))
        )
        Spacer(modifier = modifier.height(4.dp))
        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(36.dp)
                .background(
                    color = if (isSelected) MaterialTheme.colors.primary
                    else Color.Transparent
                )
                .clickable { },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = dayValue.toString(),
                style = MaterialTheme.typography.button.copy(
                    color = colorResource(id = if (isSelected) R.color.white else R.color.Black900)
                ),
            )
        }
    }
}