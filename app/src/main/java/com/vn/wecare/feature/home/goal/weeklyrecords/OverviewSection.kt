package com.vn.wecare.feature.home.goal.weeklyrecords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.ui.theme.OpenSans
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun OverviewSection(
    modifier: Modifier, totalRecords: Int, progress: Float, goalStatus: GoalStatus
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.small,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(midPadding)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Total records",
                        style = MaterialTheme.typography.body1
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 32.sp,
                                    fontFamily = OpenSans,
                                    color = MaterialTheme.colors.primary
                                )
                            ) {
                                append(totalRecords.toString())
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp,
                                    fontFamily = OpenSans
                                )
                            ) {
                                append("/7 days")
                            }
                        },
                    )
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier
                        .clip(Shapes.large)
                        .background(goalStatus.color),
                ) {
                    Text(
                        modifier = modifier.padding(smallPadding),
                        text = goalStatus.value,
                        style = MaterialTheme.typography.button.copy(MaterialTheme.colors.onPrimary)
                    )
                }
            }
            Spacer(modifier = modifier.height(smallPadding))
            LinearProgressIndicator(
                modifier = modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colors.primary,
                strokeCap = StrokeCap.Round,
                progress = progress
            )
        }
    }
}