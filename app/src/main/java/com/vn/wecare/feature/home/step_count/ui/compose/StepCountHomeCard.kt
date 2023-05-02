package com.vn.wecare.feature.home.step_count.ui.compose

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CircularProgressAnimated

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FootStepCountHomeCard(
    modifier: Modifier, onCardClick: () -> Unit, viewModel: StepCountViewModel
) {

    val stepsCountUiState = viewModel.stepsCountUiState.collectAsState().value

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = smallElevation,
        shape = Shapes.medium,
        onClick = onCardClick
    ) {
        Row(
            modifier = modifier
                .heightIn()
                .fillMaxWidth()
                .padding(horizontal = normalPadding, vertical = midPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter, modifier = modifier.weight(1f)
            ) {
                CircularProgressAnimated(
                    size = 160.dp, currentValue = viewModel.getProgressWithIndexAndGoal(
                        stepsCountUiState.currentSteps.toFloat(),
                        stepsCountUiState.stepGoal.toFloat()
                    ), indicatorThickness = 15.dp
                )
                CircularProgressAnimated(
                    size = 130.dp,
                    color = colorResource(id = R.color.Red400),
                    currentValue = viewModel.getProgressWithIndexAndGoal(
                        stepsCountUiState.caloConsumed.toFloat(),
                        stepsCountUiState.caloriesBurnedGoal.toFloat()
                    ),
                    indicatorThickness = 15.dp
                )
                CircularProgressAnimated(
                    size = 100.dp,
                    color = colorResource(id = R.color.Blue400),
                    currentValue = viewModel.getProgressWithIndexAndGoal(
                        stepsCountUiState.moveMin.toFloat(),
                        stepsCountUiState.moveTimeGoal.toFloat()
                    ),
                    indicatorThickness = 15.dp
                )
            }
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(start = halfMidPadding)
            ) {
                FootstepCountOverviewItem(
                    iconRes = R.drawable.ic_step,
                    iconColorRes = R.color.Green500,
                    index = stepsCountUiState.currentSteps,
                    unitRes = R.string.footstep_unit,
                    modifier = modifier
                )
                Spacer(modifier = modifier.height(smallPadding))
                FootstepCountOverviewItem(
                    iconRes = R.drawable.ic_fire_calo,
                    iconColorRes = R.color.Red400,
                    index = stepsCountUiState.caloConsumed,
                    unitRes = R.string.calo_unit,
                    modifier = modifier
                )
                Spacer(modifier = modifier.height(smallPadding))
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
        verticalAlignment = Alignment.Bottom
    ) {
        Icon(
            modifier = modifier
                .padding(end = smallPadding)
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
            style = MaterialTheme.typography.body2.copy(color = colorResource(id = R.color.Black450)),
        )
    }
}