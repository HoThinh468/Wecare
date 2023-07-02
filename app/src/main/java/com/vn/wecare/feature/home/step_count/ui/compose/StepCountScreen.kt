package com.vn.wecare.feature.home.step_count.ui.compose

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.home.step_count.StepCountViewModel
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.ui.theme.*
import com.vn.wecare.utils.common_composable.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun StepCountScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    stepCountViewModel: StepCountViewModel,
) {
    val stepsCountUiState = stepCountViewModel.stepsCountUiState.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val showModalBottomSheet = rememberSaveable {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(modifier = modifier,
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        topBar = {
            WecareAppBar(
                modifier = modifier,
                trailingIconRes = R.drawable.ic_edit_calendar,
                title = stepsCountUiState.value.selectedDay,
                onLeadingIconPress = navigateUp,
                onTrailingIconPress = {
                    datePicker(
                        context = context,
                        updateDate = stepCountViewModel::onDaySelected,
                    ).show()
                }
            )
        }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(smallPadding), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (stepsCountUiState.value.hasData) {
                Spacer(modifier = modifier.height(halfMidPadding))
                Overview(modifier = modifier, viewModel = stepCountViewModel)
                Spacer(modifier = modifier.height(halfMidPadding))
                SetYourGoal(modifier = modifier) {
                    showModalBottomSheet.value = !showModalBottomSheet.value
                    scope.launch { bottomSheetState.show() }
                }
                Spacer(modifier = modifier.height(halfMidPadding))
                DetailStatistic(
                    modifier = modifier, hoursList = stepsCountUiState.value.hoursList
                )
                Spacer(modifier = modifier.height(halfMidPadding))
                HealthTip(modifier = modifier)
                Spacer(modifier = modifier.height(normalPadding))
            } else {
                PageNotFound()
            }
        }
    }
}

@Composable
fun Overview(
    modifier: Modifier, viewModel: StepCountViewModel
) {

    val stepsCountUiState = viewModel.stepsCountUiState.collectAsState().value

    Card(
        modifier = modifier.fillMaxWidth(),
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
                    size = 180.dp, currentValue = viewModel.getProgressWithIndexAndGoal(
                        stepsCountUiState.currentSteps.toFloat(),
                        stepsCountUiState.stepGoal.toFloat()
                    ), indicatorThickness = 20.dp
                )
                CircularProgressAnimated(
                    size = 140.dp,
                    color = colorResource(id = R.color.Red400),
                    currentValue = viewModel.getProgressWithIndexAndGoal(
                        stepsCountUiState.caloConsumed.toFloat(),
                        stepsCountUiState.caloriesBurnedGoal.toFloat()
                    ),
                    indicatorThickness = 20.dp
                )
                CircularProgressAnimated(
                    size = 100.dp,
                    color = colorResource(id = R.color.Blue400),
                    currentValue = viewModel.getProgressWithIndexAndGoal(
                        stepsCountUiState.moveMin.toFloat(),
                        stepsCountUiState.moveTimeGoal.toFloat()
                    ),
                    indicatorThickness = 20.dp
                )
            }
            Row(
                modifier = modifier
                    .padding(
                        end = mediumPadding, start = mediumPadding, top = largePadding
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepCountIndexItem(
                    iconRes = R.drawable.ic_fire_calo,
                    titleRes = R.string.calo_amount_title,
                    iconColorRes = R.color.Red400,
                    index = stepsCountUiState.caloConsumed,
                    goal = stepsCountUiState.caloriesBurnedGoal,
                    unitRes = R.string.calo_unit,
                    modifier = modifier
                )
                StepCountIndexItem(
                    iconRes = R.drawable.ic_step,
                    titleRes = R.string.footstep_title,
                    iconColorRes = R.color.Green500,
                    index = stepsCountUiState.currentSteps,
                    goal = stepsCountUiState.stepGoal,
                    unitRes = null,
                    modifier = modifier
                )
                StepCountIndexItem(
                    iconRes = R.drawable.ic_time_clock,
                    titleRes = R.string.move_min_title,
                    iconColorRes = R.color.Blue400,
                    index = stepsCountUiState.moveMin,
                    goal = stepsCountUiState.moveTimeGoal,
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
        modifier = modifier.widthIn(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = modifier.size(16.dp),
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = colorResource(
                    id = iconColorRes
                )
            )
            Spacer(modifier = modifier.width(4.dp))
            Text(
                text = stringResource(id = titleRes), style = MaterialTheme.typography.body2
            )
        }
        Text(text = index.toString(), style = MaterialTheme.typography.h3)
        Text(
            text = if (unitRes == null) "/$goal" else "/$goal " + stringResource(id = unitRes),
            style = MaterialTheme.typography.body1.copy(
                color = colorResource(id = R.color.Black450)
            )
        )
    }
}

@Composable
fun SetYourGoal(
    modifier: Modifier, moveToSetGoalScreen: () -> Unit
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
fun DetailStatistic(
    modifier: Modifier, hoursList: List<StepsPerHour>
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = Shapes.small,
    ) {
        DailyBarChart(
            modifier = modifier, dataList = hoursList
        )
    }
}