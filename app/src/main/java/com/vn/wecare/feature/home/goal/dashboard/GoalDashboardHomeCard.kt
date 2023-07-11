package com.vn.wecare.feature.home.goal.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vn.wecare.R
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalDashboardHomeCard(
    modifier: Modifier, onCardClick: () -> Unit
) {
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
            Column(
                modifier = modifier.height(IntrinsicSize.Max),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "My goal",
                    style = MaterialTheme.typography.h5,
                )
                Text(
                    modifier = modifier.padding(top = smallPadding),
                    text = LatestGoalSingletonObject.getInStance().goalName,
                    style = MaterialTheme.typography.h1.copy(
                        color = MaterialTheme.colors.primary
                    )
                )
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