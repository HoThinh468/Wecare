package com.vn.wecare.utils.common_composable

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.ui.theme.*

@Composable
fun DailyBarChart(
    modifier: Modifier, color: Color = MaterialTheme.colors.primary, dataList: List<StepsPerHour>
) {
    val maxValue = if (dataList.isNotEmpty()) dataList.maxOf { it.steps } else 0

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(275.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(bottom = extraLargePadding, end = normalPadding, top = midPadding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Column(
                modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End
            ) {
                Text(text = "$maxValue", style = MaterialTheme.typography.caption)
                Divider(
                    modifier = modifier.fillMaxWidth(),
                    color = colorResource(id = R.color.Grey100),
                    thickness = 1.dp
                )
            }
            Column(
                modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End
            ) {
                Text(text = "${maxValue / 2}", style = MaterialTheme.typography.caption)
                Divider(
                    modifier = modifier.fillMaxWidth(),
                    color = colorResource(id = R.color.Grey100),
                    thickness = 1.dp
                )
            }
            Text(text = "0", style = MaterialTheme.typography.caption)
        }
        Box(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.CenterStart)
                .padding(
                    bottom = midPadding, end = if (maxValue >= 10000) xxxExtraPadding
                    else if (maxValue >= 1000) xxExtraPadding
                    else extraLargePadding, start = normalPadding
                ),
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .horizontalScroll(rememberScrollState()), verticalAlignment = Alignment.Bottom
            ) {
                dataList.forEach {
                    Column(
                        modifier = modifier.padding(end = smallPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = modifier.padding(bottom = tinyPadding).width(10.dp)
                                .height(((it.steps.toFloat() / maxValue.toFloat()) * 200).dp)
                                .clip(RoundedCornerShape(smallRadius)).background(color),
                        )
                        Text(text = "$:00", style = MaterialTheme.typography.caption)
                    }
                }
            }
        }
    }
}