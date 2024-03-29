package com.vn.wecare.feature.home.goal.dashboard.goaldetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlagCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.feature.home.goal.dashboard.GoalDetailUiState
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.ui.theme.OpenSans
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun GoalOverview(modifier: Modifier, detailUi: GoalDetailUiState) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.small,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(midPadding)
        ) {
            GoalDetailHeadlineItem(
                modifier = modifier,
                icon = Icons.Default.FlagCircle,
                iconColor = MaterialTheme.colors.primary,
                headline = "Goal Overview"
            )
            Spacer(modifier = modifier.height(smallPadding))
            Text(
                text = detailUi.description,
                style = MaterialTheme.typography.body2.copy(color = colorResource(id = R.color.Black450))
            )
            Spacer(modifier = modifier.height(smallPadding))
            Box(
                modifier = modifier
                    .clip(Shapes.medium)
                    .background(GoalStatus.getGoalStatusFromValue(detailUi.status).color)
            ) {
                Text(
                    modifier = modifier.padding(vertical = 6.dp, horizontal = smallPadding),
                    text = detailUi.status,
                    style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.onPrimary)
                )
            }
            if (detailUi.status == GoalStatus.INPROGRESS.value) {
                Spacer(modifier = modifier.height(smallPadding))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 32.sp,
                                fontFamily = OpenSans
                            )
                        ) {
                            append(detailUi.dayLeft.toString())
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                fontFamily = OpenSans
                            )
                        ) {
                            append(" days left")
                        }
                    },
                )
                Spacer(modifier = modifier.height(smallPadding))
                LinearProgressIndicator(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(6.dp),
                    color = MaterialTheme.colors.primary,
                    strokeCap = StrokeCap.Round,
                    progress = detailUi.timeProgress
                )
            }
        }
    }
}