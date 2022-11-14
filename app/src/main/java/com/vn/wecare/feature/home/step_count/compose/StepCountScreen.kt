package com.vn.wecare.feature.home.step_count.compose

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vn.wecare.R
import com.vn.wecare.feature.home.step_count.MotionSensorTrack
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.StepsCountUiState
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun StepCountScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    moveToSetGoalScreen: () -> Unit,
    stepCountViewModel: StepCountViewModel = viewModel()
) {
    val stepsCountUiState = stepCountViewModel.stepsCountUiState.collectAsState()

    Scaffold(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        topBar = {
            StepCountAppBar(modifier) { navigateUp() }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(smallPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MotionSensorTrack(stepCountViewModel = stepCountViewModel)
            Spacer(modifier = modifier.height(halfMidPadding))
            Overview(modifier = modifier, stepsCountUiState = stepsCountUiState.value)
            Spacer(modifier = modifier.height(halfMidPadding))
            SetYourGoal(modifier = modifier) {
                moveToSetGoalScreen()
            }
            Spacer(modifier = modifier.height(halfMidPadding))
            DetailStatistic(modifier = modifier)
            Spacer(modifier = modifier.height(halfMidPadding))
            HealthTip(modifier = modifier)
            Spacer(modifier = modifier.height(normalPadding))
        }
    }
}

@Composable
fun StepCountAppBar(
    modifier: Modifier,
    navigateUp: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = smallPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = navigateUp) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
            Text(text = "Oct 12, 2022", style = MaterialTheme.typography.h4)
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit_calendar),
                    contentDescription = null
                )
            }
        }
        Divider(color = colorResource(id = R.color.Grey100), thickness = 1.dp)
        CalendarWeekView(modifier = modifier)
    }
}

@Composable
fun Overview(
    modifier: Modifier,
    stepsCountUiState: StepsCountUiState
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = Shapes.small,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = largePadding)
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = modifier.padding(bottom = normalPadding)
            ) {
                CircularProgressAnimated(
                    size = 200.dp,
                    currentValue = 75f,
                    indicatorThickness = 20.dp
                )
                CircularProgressAnimated(
                    size = 160.dp,
                    color = colorResource(id = R.color.Red400),
                    currentValue = 30f,
                    indicatorThickness = 20.dp
                )
                CircularProgressAnimated(
                    size = 120.dp,
                    color = colorResource(id = R.color.Blue400),
                    currentValue = 50f,
                    indicatorThickness = 20.dp
                )
            }
            Row(
                modifier = modifier
                    .padding(end = mediumPadding, start = mediumPadding, top = largePadding)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepCountIndexItem(
                    iconRes = R.drawable.ic_step,
                    titleRes = R.string.footstep_title,
                    iconColorRes = R.color.Green500,
                    index = stepsCountUiState.currentSteps,
                    goal = 12000,
                    unitRes = null,
                    modifier = modifier
                )
                StepCountIndexItem(
                    iconRes = R.drawable.ic_fire_calo,
                    titleRes = R.string.calo_amount_title,
                    iconColorRes = R.color.Red400,
                    index = 560,
                    goal = 1000,
                    unitRes = R.string.calo_unit,
                    modifier = modifier
                )
                StepCountIndexItem(
                    iconRes = R.drawable.ic_time_clock,
                    titleRes = R.string.move_min_title,
                    iconColorRes = R.color.Blue400,
                    index = 90,
                    goal = 80,
                    unitRes = R.string.move_time_unit,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun StepCountIndexItem(
    @DrawableRes iconRes: Int,
    @StringRes titleRes: Int,
    @ColorRes iconColorRes: Int,
    @StringRes unitRes: Int?,
    index: Int,
    goal: Int,
    modifier: Modifier
) {
    Column(
        modifier = modifier.widthIn(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = colorResource(
                    id = iconColorRes
                )
            )
            Spacer(modifier = modifier.width(4.dp))
            Text(
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.body1
            )
        }
        Text(text = index.toString(), style = MaterialTheme.typography.h2)
        Text(
            text = if (unitRes == null) "/$goal" else "/$goal " + stringResource(id = unitRes),
            style = MaterialTheme.typography.h4.copy(
                color = colorResource(id = R.color.Black450)
            )
        )
    }
}

@Composable
fun SetYourGoal(
    modifier: Modifier,
    moveToSetGoalScreen: () -> Unit
) {
    CardListTile(
        modifier = modifier,
        leadingIconRes = R.drawable.ic_fact_check,
        trailingIconRes = R.drawable.ic_arrow_forward,
        titleRes = R.string.set_goals,
        subTitle = stringResource(id = R.string.set_goal_subtitle),
        colorIconRes = R.color.Pink,
        onClick = moveToSetGoalScreen,
        elevation = null
    )
}

@Composable
fun HealthTip(
    modifier: Modifier
) {
    CardListTile(
        modifier = modifier,
        leadingIconRes = R.drawable.ic_tips_and_updates,
        trailingIconRes = null,
        titleRes = R.string.health_tip,
        subTitle = stringResource(id = R.string.step_health_tip),
        colorIconRes = R.color.Orange300,
        elevation = null
    )
}

@Composable
fun DetailStatistic(modifier: Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = Shapes.small,
    ) {
        val dataList = listOf(
            StepCountPerHour(0, 1000),
            StepCountPerHour(1, 1000),
            StepCountPerHour(2, 2000),
            StepCountPerHour(3, 500),
            StepCountPerHour(4, 400),
            StepCountPerHour(5, 1000),
            StepCountPerHour(6, 1000),
            StepCountPerHour(7, 0),
            StepCountPerHour(8, 0),
            StepCountPerHour(9, 1200),
            StepCountPerHour(10, 0),
            StepCountPerHour(11, 700),
            StepCountPerHour(12, 800),
            StepCountPerHour(13, 900),
            StepCountPerHour(14, 780),
            StepCountPerHour(15, 1000),
            StepCountPerHour(16, 200),
            StepCountPerHour(17, 1000),
            StepCountPerHour(18, 0),
            StepCountPerHour(19, 0),
            StepCountPerHour(20, 1000),
            StepCountPerHour(21, 1200),
            StepCountPerHour(22, 1020),
            StepCountPerHour(23, 1050)
        )
        DailyBarChart(
            modifier = modifier,
            dataList = dataList
        )
    }
}