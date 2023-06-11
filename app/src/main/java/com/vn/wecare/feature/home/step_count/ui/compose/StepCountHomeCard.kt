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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StepCountHomeCard(
    modifier: Modifier, onCardClick: () -> Unit, steps: Int, calories: Int, time: Int
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = smallElevation,
        shape = Shapes.medium,
        onClick = onCardClick
    ) {
        Column(
            modifier = modifier
                .heightIn()
                .fillMaxWidth()
                .padding(normalPadding)
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Pedometers",
                style = MaterialTheme.typography.h5,
            )
            Spacer(modifier = modifier.height(normalPadding))
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = normalPadding),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FootstepCountOverviewItem(
                    iconRes = R.drawable.ic_fire_calo,
                    iconColorRes = R.color.Red400,
                    index = calories,
                    unitRes = R.string.calo_unit,
                    modifier = modifier
                )
                FootstepCountOverviewItem(
                    iconRes = R.drawable.ic_step,
                    iconColorRes = R.color.Green500,
                    index = steps,
                    unitRes = R.string.footstep_unit,
                    modifier = modifier
                )
                FootstepCountOverviewItem(
                    iconRes = R.drawable.ic_time_clock,
                    iconColorRes = R.color.Blue400,
                    index = time,
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = modifier
                .padding(bottom = 4.dp)
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