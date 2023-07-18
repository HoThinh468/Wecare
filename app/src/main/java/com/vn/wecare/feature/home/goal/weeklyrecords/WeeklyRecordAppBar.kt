package com.vn.wecare.feature.home.goal.weeklyrecords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat2
import com.vn.wecare.ui.theme.halfMediumIconSize
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallIconSize
import com.vn.wecare.utils.common_composable.WecareAppBar

@Composable
fun WeeklyRecordAppBar(
    modifier: Modifier, navigateBack: () -> Unit, startDay: Long, endDay: Long
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        WecareAppBar(
            modifier = modifier, onLeadingIconPress = navigateBack, title = "Weekly report"
        )
        Divider()
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = normalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /*TODO*/ }, modifier = modifier.size(halfMediumIconSize)) {
                Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = null)
            }
            Text(
                text = "${getDayFromLongWithFormat2(startDay)} - ${getDayFromLongWithFormat2(endDay)}",
                style = MaterialTheme.typography.h5
            )
            IconButton(onClick = { /*TODO*/ }, modifier = modifier.size(halfMediumIconSize)) {
                Icon(imageVector = Icons.Default.ArrowForwardIos, contentDescription = null)
            }
        }
    }
}