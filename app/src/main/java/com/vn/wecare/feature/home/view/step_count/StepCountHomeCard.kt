package com.vn.wecare.feature.home.view.step_count

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
            .padding(top = midPadding),
        elevation = smallElevation,
        shape = Shapes.small,
        onClick = onCardClick
    ) {
        Row(
            modifier = modifier
                .heightIn()
                .fillMaxWidth()
                .padding(horizontal = midPadding, vertical = largePadding)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter
            ) {
                CircularProgressAnimated(
                    size = 100.dp,
                    currentValue = 75f,
                )
                CircularProgressAnimated(
                    size = 75.dp,
                    color = colorResource(id = R.color.Red400),
                    currentValue = 30f,
                )
                CircularProgressAnimated(
                    size = 50.dp,
                    color = colorResource(id = R.color.Blue400),
                    currentValue = 50f,
                )
            }
            FootstepCountOverviewItem(
                iconRes = R.drawable.ic_step,
                titleRes = R.string.footstep_title,
                iconColorRes = R.color.Green500,
                index = 4056000,
                modifier = modifier
            )
            FootstepCountOverviewItem(
                iconRes = R.drawable.ic_fire_calo,
                titleRes = R.string.calo_amount_title,
                iconColorRes = R.color.Red400,
                index = 1000,
                modifier = modifier
            )
            FootstepCountOverviewItem(
                iconRes = R.drawable.ic_time_clock,
                titleRes = R.string.move_min_title,
                iconColorRes = R.color.Blue400,
                index = 1000,
                modifier = modifier
            )
        }
    }
}

@Composable
fun FootstepCountOverviewItem(
    @DrawableRes iconRes: Int,
    @StringRes titleRes: Int,
    @ColorRes iconColorRes: Int,
    index: Int,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = modifier.padding(bottom = 2.dp),
            text = index.toString(),
            style = MaterialTheme.typography.h3,
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = modifier
                    .padding(end = 2.dp)
                    .size(iconSize),
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = colorResource(id = iconColorRes)
            )
            Text(
                text = stringResource(id = titleRes),
                style = MaterialTheme
                    .typography.body1.copy(
                        color = colorResource(id = R.color.Black450)
                    )
            )
        }
    }
}