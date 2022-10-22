package com.vn.wecare.feature.home.view.step_count

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.vn.wecare.R
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.common_composable.CardListTile

@Composable
fun SetYourGoalScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        topBar = { SetYourGoalAppBar(modifier = modifier, navigateUp = navigateUp) }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(smallPadding)
        ) {
            Spacer(modifier = modifier.height(smallPadding))
            CardListTile(
                modifier = modifier,
                leadingIconRes = R.drawable.ic_step,
                trailingIconRes = R.drawable.ic_arrow_forward,
                titleRes = R.string.footstep_title,
                subTitle = "6000",
                elevation = smallElevation
            )
            Spacer(modifier = modifier.height(halfMidPadding))
            CardListTile(
                modifier = modifier,
                leadingIconRes = R.drawable.ic_fire_calo,
                trailingIconRes = R.drawable.ic_arrow_forward,
                titleRes = R.string.calo_amount_title,
                subTitle = "500 kcal",
                colorIconRes = R.color.Red400,
                elevation = smallElevation
            )
            Spacer(modifier = modifier.height(halfMidPadding))
            CardListTile(
                modifier = modifier,
                leadingIconRes = R.drawable.ic_time_clock,
                trailingIconRes = R.drawable.ic_arrow_forward,
                titleRes = R.string.move_min_title,
                subTitle = "30 min",
                colorIconRes = R.color.Blue400,
                elevation = smallElevation
            )
        }
    }
}

@Composable
fun SetYourGoalAppBar(
    modifier: Modifier,
    navigateUp: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = R.string.set_goals))
        },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background,
    )
}