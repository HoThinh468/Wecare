package com.vn.wecare.feature.home.goal.dashboard

import androidx.compose.foundation.Image
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
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.feature.account.view.goalhistory.getColorBasedOnStatus
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalDashboardHomeCard(
    modifier: Modifier, onCardClick: () -> Unit
) {
    val goal = LatestGoalSingletonObject.getInStance()

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = smallElevation,
        shape = Shapes.medium,
        onClick = onCardClick
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = goal.goalName, style = MaterialTheme.typography.h2
                )
                Spacer(modifier = modifier.height(smallPadding))
                Box(
                    modifier = modifier
                        .clip(Shapes.medium)
                        .background(getColorBasedOnStatus(status = goal.goalStatus))
                ) {
                    Text(
                        modifier = modifier.padding(vertical = 6.dp, horizontal = smallPadding),
                        text = goal.goalStatus,
                        style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.onPrimary)
                    )
                }
                Spacer(modifier = modifier.height(halfMidPadding))
                Row(
                    modifier = modifier
                        .width(IntrinsicSize.Max)
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            "From:", style = MaterialTheme.typography.caption.copy(
                                color = colorResource(
                                    id = R.color.Black450
                                )
                            )
                        )
                        Text(
                            getDayFromLongWithFormat(goal.dateSetGoal),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Divider(
                        modifier = modifier
                            .fillMaxHeight()
                            .padding(horizontal = normalPadding)
                            .width(1.dp)
                    )
                    Column {
                        Text(
                            "To:", style = MaterialTheme.typography.caption.copy(
                                color = colorResource(
                                    id = R.color.Black450
                                )
                            )
                        )
                        Text(
                            getDayFromLongWithFormat(goal.dateEndGoal),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
            Image(
                modifier = modifier.size(100.dp),
                painter = painterResource(id = getDrawableRes()),
                contentDescription = null
            )
        }
    }
}

private fun getDrawableRes(): Int {
    return when (LatestGoalSingletonObject.getInStance().goalName) {
        EnumGoal.GAINMUSCLE.value -> R.drawable.img_illu_muscle
        EnumGoal.LOSEWEIGHT.value -> R.drawable.img_illu_loose_weight
        EnumGoal.GETHEALTHIER.value -> R.drawable.img_illu_healthier
        else -> R.drawable.img_illu_improve_mood
    }
}