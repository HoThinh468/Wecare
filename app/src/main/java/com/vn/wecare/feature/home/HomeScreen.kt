package com.vn.wecare.feature.home

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.bmi.YourBMIHomeCard
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.ui.compose.FootStepCountHomeCard
import com.vn.wecare.feature.home.water.WaterOverviewHomeCard
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.CustomOutlinedIconButton
import com.vn.wecare.utils.common_composable.RequestPermission

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onFootStepCountCardClick: () -> Unit,
    onWaterCardClick: () -> Unit,
    onBMICardClick: () -> Unit,
    onTrainingClick: () -> Unit,
    onWalkingIcClick: () -> Unit,
    onRunningIcClick: () -> Unit,
    onBicycleIcClick: () -> Unit,
    onMeditationIcClick: () -> Unit,
    stepCountViewModel: StepCountViewModel,
    moveToAccountScreen: () -> Unit
) {
    val stepsCountUiState = stepCountViewModel.stepsCountUiState.collectAsState()

    RequestPermission(permission = Manifest.permission.ACTIVITY_RECOGNITION)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(halfMidPadding),
    ) {
        HomeHeader(modifier = modifier, moveToAccountScreen = moveToAccountScreen)
        FootStepCountHomeCard(
            modifier = modifier,
            onCardClick = onFootStepCountCardClick,
            steps = stepsCountUiState.value.currentSteps,
            calories = stepsCountUiState.value.caloConsumed
        )
        TrainingNow(
            modifier = modifier,
            onTrainingClick,
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
    moveToAccountScreen: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
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
        IconButton(
            onClick = moveToAccountScreen,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrainingNow(
    modifier: Modifier,
    onTrainingClick: () -> Unit,
    onWalkingIcClick: () -> Unit,
    onRunningIcClick: () -> Unit,
    onBicycleIcClick: () -> Unit,
    onMeditationIcClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = smallPadding),
        elevation = smallElevation,
        shape = Shapes.small,
        onClick = onTrainingClick
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