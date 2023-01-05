package com.vn.wecare.feature.home.step_count.ui.compose

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.step_count.StepsCountUiState
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CircularProgressAnimated

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FootStepCountHomeCard(
    modifier: Modifier, onCardClick: () -> Unit, stepsCountUiState: StepsCountUiState
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = midPadding),
        elevation = smallElevation,
        shape = Shapes.small,
        onClick = onCardClick
    ) {
        Row(
            modifier = modifier
                .heightIn()
                .fillMaxWidth()
                .padding(horizontal = normalPadding, vertical = mediumPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter, modifier = modifier.weight(1f)
            ) {
                CircularProgressAnimated(
                    size = 125.dp,
                    currentValue = if (stepsCountUiState.currentSteps > stepsCountUiState.stepGoal) 100f
                    else (stepsCountUiState.currentSteps.toFloat() / stepsCountUiState.stepGoal),
                )
                CircularProgressAnimated(
                    size = 100.dp,
                    color = colorResource(id = R.color.Red400),
                    currentValue = if (stepsCountUiState.caloConsumed > stepsCountUiState.caloriesBurnedGoal) 100f
                    else (stepsCountUiState.caloConsumed.toFloat() / stepsCountUiState.caloriesBurnedGoal),
                )
                CircularProgressAnimated(
                    size = 75.dp,
                    color = colorResource(id = R.color.Blue400),
                    currentValue = if (stepsCountUiState.moveMin > stepsCountUiState.moveTimeGoal) 100f
                    else (stepsCountUiState.moveMin.toFloat() / stepsCountUiState.moveTimeGoal),
                )
            }
            Column(
                modifier = modifier.weight(1.2f)
            ) {
                FootstepCountOverviewItem(
                    iconRes = R.drawable.ic_step,
                    iconColorRes = R.color.Green500,
                    index = stepsCountUiState.currentSteps,
                    unitRes = R.string.footstep_unit,
                    modifier = modifier
                )
                FootstepCountOverviewItem(
                    iconRes = R.drawable.ic_fire_calo,
                    iconColorRes = R.color.Red400,
                    index = stepsCountUiState.caloConsumed,
                    unitRes = R.string.calo_unit,
                    modifier = modifier
                )
                FootstepCountOverviewItem(
                    iconRes = R.drawable.ic_time_clock,
                    iconColorRes = R.color.Blue400,
                    index = stepsCountUiState.moveMin,
                    unitRes = R.string.move_time_unit,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun FootstepCountOverviewItem(
    @DrawableRes iconRes: Int,
    @StringRes unitRes: Int,
    @ColorRes iconColorRes: Int,
    index: Int,
    modifier: Modifier
) {
    Row(
        modifier = modifier.padding(start = smallPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = modifier
                .padding(end = 2.dp)
                .size(iconSize),
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = colorResource(id = iconColorRes)
        )
        Text(
            text = "$index ",
            style = MaterialTheme.typography.h4,
        )
        Text(
            text = stringResource(id = unitRes),
            style = MaterialTheme.typography.body1.copy(color = colorResource(id = R.color.Black450)),
        )
    }
}