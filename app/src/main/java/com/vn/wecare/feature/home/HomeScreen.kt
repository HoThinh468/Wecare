package com.vn.wecare.feature.home

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.bmi.ui.YourBMIHomeCard
import com.vn.wecare.feature.home.goal.dashboard.GoalDashboardHomeCard
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.step_count.ui.compose.DailyCalories
import com.vn.wecare.feature.home.step_count.ui.compose.StepCountHomeCard
import com.vn.wecare.feature.home.water.WaterOverviewHomeCard
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.CustomOutlinedIconButton
import com.vn.wecare.utils.common_composable.RequestPermission

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onDashboardCardClick: () -> Unit,
    onWaterCardClick: () -> Unit,
    onBMICardClick: () -> Unit,
    homeViewModel: HomeViewModel,
) {
    RequestPermission(permission = Manifest.permission.ACTIVITY_RECOGNITION)

    val homeUIState = homeViewModel.homeUiState.collectAsState().value

    when (homeViewModel.updateStepsResponse) {
        is Response.Success -> {
            Log.e("Update steps count: ", "Success")
        }

        is Response.Error -> {
            Log.e("Update steps count: ", "Failed")
        }

        else -> {}
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        topBar = {
            HomeHeader(
                modifier = modifier,
            )
        }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = midPadding, vertical = smallPadding),
        ) {
            when (homeViewModel.getCaloPerDayResponse) {
                is Response.Success -> {
                    val caloInfo = homeViewModel.caloPerDay.collectAsState().value
                    val goal = LatestGoalSingletonObject.getInStance()
                    val caloOut = caloInfo?.caloOut ?: 0

                    println("caloInfo: $caloInfo")
                    DailyCalories(
                        remainingCalo = (goal.caloriesBurnedEachDayGoal - caloOut).let {
                            if (it < 0) 0 else it
                        },
                        caloriesIn = caloInfo?.caloInt ?: 0,
                        caloriesInProgress = caloInfo?.caloInt?.let {
                            (it.toFloat() / goal.caloriesInEachDayGoal.toFloat()) + 0.01f
                        } ?: 0.01f,
                        caloriesOut = caloOut,
                        caloriesOutProgress = (caloOut.toFloat() / goal.caloriesBurnedEachDayGoal.toFloat()) + 0.01f,
                        caloriesOutTarget = goal.caloriesBurnedEachDayGoal.toFloat(),

                        )
                }

                is Response.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Response.Error -> {}

            }
            Spacer(modifier = modifier.height(normalPadding))
            GoalDashboardHomeCard(
                modifier = modifier,
                onCardClick = { onDashboardCardClick() },
            )
            YourBMIHomeCard(
                modifier = modifier, onCardClick = onBMICardClick, bmiIndex = homeUIState.bmiIndex
            )
            WaterOverviewHomeCard(
                modifier = modifier,
                onCardClick = onWaterCardClick,
                currentIndex = homeUIState.waterIndex,
                targetAmount = homeUIState.waterTargetAmount,
                onAddAmountClick = {
                    homeViewModel.addNewWaterRecord()
                },
                onMinusAmountClick = {
                    homeViewModel.deleteLatestRecord()
                },
            )
            Spacer(modifier = modifier.height(largePadding))
        }
    }
}

@Composable
fun HomeHeader(
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = midPadding, vertical = halfMidPadding),
        horizontalArrangement = Arrangement.Start,
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TrainingNow(
    modifier: Modifier = Modifier,
    onTrainingClick: () -> Unit,
    onWalkingIcClick: () -> Unit,
    onRunningIcClick: () -> Unit,
    onBicycleIcClick: () -> Unit,
    onMeditationIcClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = smallElevation,
        shape = Shapes.medium,
        onClick = onTrainingClick
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(halfMidPadding),
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
                CustomOutlinedIconButton(
                    modifier = modifier, iconRes = R.drawable.ic_walk, onClick = onWalkingIcClick
                )
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