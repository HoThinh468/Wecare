package com.vn.wecare.feature.home.step_count

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
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.CircularProgressAnimated

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FootStepCountHomeCard(
    modifier: Modifier,
    onCardClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = normalPadding),
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
                contentAlignment = Alignment.BottomCenter,
                modifier = modifier.weight(1f)
            ) {
                CircularProgressAnimated(
                    size = 125.dp,
                    currentValue = 75f,
                )
                CircularProgressAnimated(
                    size = 100.dp,
                    color = colorResource(id = R.color.Red400),
                    currentValue = 30f,
                )
                CircularProgressAnimated(
                    size = 75.dp,
                    color = colorResource(id = R.color.Blue400),
                    currentValue = 50f,
                )
            }
            Column(
                modifier = modifier.weight(1.2f)
            ) {
                FootstepCountOverviewItem(
                    iconRes = R.drawable.ic_step,
                    iconColorRes = R.color.Green500,
                    index = 4056000,
                    unitRes = R.string.footstep_unit,
                    modifier = modifier
                )
                FootstepCountOverviewItem(
                    iconRes = R.drawable.ic_fire_calo,
                    iconColorRes = R.color.Red400,
                    index = 1000,
                    unitRes = R.string.calo_unit,
                    modifier = modifier
                )
                FootstepCountOverviewItem(
                    iconRes = R.drawable.ic_time_clock,
                    iconColorRes = R.color.Blue400,
                    index = 1000,
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