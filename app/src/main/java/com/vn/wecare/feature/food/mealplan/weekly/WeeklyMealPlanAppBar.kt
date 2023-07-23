package com.vn.wecare.feature.food.mealplan.weekly

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
import com.vn.wecare.ui.theme.halfMediumIconSize
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.utils.common_composable.WecareAppBar

@Composable
fun WeeklyMealPlanAppBar(
    modifier: Modifier,
    navigateUp: () -> Unit,
    firstDayOfWeeK: String,
    lastDayOfWeek: String,
    onPreviousWeekClick: () -> Unit,
    onNextWeekClick: () -> Unit,
    isNextWeekClickEnable: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
    ) {
        WecareAppBar(
            modifier = modifier,
            title = "Weekly meal plan",
            onLeadingIconPress = navigateUp,
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = normalPadding),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onPreviousWeekClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIos,
                    contentDescription = null,
                    modifier = modifier.size(
                        halfMediumIconSize
                    )
                )
            }
            Text(
                text = "$firstDayOfWeeK - $lastDayOfWeek",
                modifier = modifier.padding(horizontal = normalPadding),
                style = MaterialTheme.typography.body1
            )
            IconButton(onClick = { onNextWeekClick() }, enabled = isNextWeekClickEnable) {
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = null,
                    modifier = modifier.size(
                        halfMediumIconSize
                    )
                )
            }
        }
        Divider()
    }
}