package com.vn.wecare.feature.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.bmi.ui.YourBMIHomeCard
import com.vn.wecare.feature.home.bmi.viewmodel.BMIViewModel
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.ui.compose.FootStepCountHomeCard
import com.vn.wecare.feature.home.water.WaterOverviewHomeCard
import com.vn.wecare.feature.home.water.tracker.WaterViewModel
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.CustomOutlinedIconButton
import com.vn.wecare.utils.common_composable.RequestPermission

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
    cancelInExactAlarm: () -> Unit,
    homeViewModel: HomeViewModel,
    stepCountViewModel: StepCountViewModel,
    waterViewModel: WaterViewModel,
    bmiViewModel: BMIViewModel
) {
    RequestPermission(permission = Manifest.permission.ACTIVITY_RECOGNITION)

    Scaffold(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background),
        topBar = { HomeHeader(modifier = modifier, cancelInExactAlarm = cancelInExactAlarm) }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = midPadding, vertical = smallPadding),
        ) {
            FootStepCountHomeCard(
                modifier = modifier,
                onCardClick = onFootStepCountCardClick,
                viewModel = stepCountViewModel
            )
            TrainingNow(
                modifier = modifier,
                onTrainingClick,
                onWalkingIcClick,
                onRunningIcClick,
                onBicycleIcClick,
                onMeditationIcClick
            )
            YourBMIHomeCard(
                modifier = modifier, onCardClick = onBMICardClick, viewModel = bmiViewModel
            )
            WaterOverviewHomeCard(
                modifier = modifier, onCardClick = onWaterCardClick, viewModel = waterViewModel
            )
            Spacer(modifier = modifier.height(largePadding))
        }
    }
}

@Composable
fun HomeHeader(
    modifier: Modifier,
    cancelInExactAlarm: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding, vertical = halfMidPadding),
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
        IconButton(onClick = cancelInExactAlarm) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = null)
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
            .padding(top = normalPadding),
        elevation = smallElevation,
        shape = Shapes.medium,
        onClick = onTrainingClick
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(midPadding),
        ) {
            Text(
                modifier = modifier.padding(bottom = normalPadding),
                text = stringResource(id = R.string.training_now),
                style = MaterialTheme.typography.h5,
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = midPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomOutlinedIconButton(modifier = modifier,
                    iconRes = R.drawable.ic_walk,
                    onClick = { })
                CustomOutlinedIconButton(
                    modifier = modifier, iconRes = R.drawable.ic_run, onClick = onRunningIcClick
                )
                CustomOutlinedIconButton(
                    modifier = modifier, iconRes = R.drawable.ic_bicycle, onClick = onBicycleIcClick
                )
                CustomOutlinedIconButton(
                    modifier = modifier,
                    iconRes = R.drawable.ic_self_improvement,
                    onClick = onMeditationIcClick
                )
            }
        }
    }
}