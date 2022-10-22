package com.vn.wecare.feature.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.view.bmi.YourBMIHomeCard
import com.vn.wecare.feature.home.view.step_count.FootStepCountHomeCard
import com.vn.wecare.feature.home.view.water.WaterOverviewHomeCard
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.CustomOutlinedIconButton

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onFootStepCountCardClick: () -> Unit,
    onWaterCardClick: () -> Unit,
    onBMICardClick: () -> Unit,
    onWalkingIcClick: () -> Unit,
    onRunningIcClick: () -> Unit,
    onBicycleIcClick: () -> Unit,
    onMeditationIcClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(midPadding),
    ) {
        HomeHeader(modifier = modifier)
        FootStepCountHomeCard(modifier = modifier, onCardClick = onFootStepCountCardClick)
        TrainingNow(
            modifier = modifier,
            onWalkingIcClick,
            onRunningIcClick,
            onBicycleIcClick,
            onMeditationIcClick
        )
        WaterOverviewHomeCard(modifier = modifier, onCardClick = onWaterCardClick)
        YourBMIHomeCard(modifier = modifier, onCardClick = onBMICardClick)
        Spacer(modifier = modifier.height(largePadding))
    }
}

@Composable
fun HomeHeader(
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier
                .height(largeIconSize)
                .width(100.dp),
            painter = painterResource(R.drawable.logo2),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun TrainingNow(
    modifier: Modifier,
    onWalkingIcClick: () -> Unit,
    onRunningIcClick: () -> Unit,
    onBicycleIcClick: () -> Unit,
    onMeditationIcClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = midPadding),
        elevation = smallElevation,
        shape = Shapes.small
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding),
        ) {
            Text(
                modifier = modifier.padding(bottom = normalPadding),
                text = stringResource(id = R.string.training_now),
                style = MaterialTheme.typography.h4,
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomOutlinedIconButton(
                    modifier = modifier,
                    iconRes = R.drawable.ic_walk,
                    trainingTitleRes = R.string.training_walk_title,
                    onClick = onWalkingIcClick
                )
                CustomOutlinedIconButton(
                    modifier = modifier,
                    iconRes = R.drawable.ic_run,
                    trainingTitleRes = R.string.training_run_title,
                    onClick = onRunningIcClick
                )
                CustomOutlinedIconButton(
                    modifier = modifier,
                    iconRes = R.drawable.ic_bicycle,
                    trainingTitleRes = R.string.training_bicycle_title,
                    onClick = onBicycleIcClick
                )
                CustomOutlinedIconButton(
                    modifier = modifier,
                    iconRes = R.drawable.ic_self_improvement,
                    trainingTitleRes = R.string.training_meditation_title,
                    onClick = onMeditationIcClick
                )
            }
        }
    }
}