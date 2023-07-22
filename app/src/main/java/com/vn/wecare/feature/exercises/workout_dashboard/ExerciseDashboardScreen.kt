package com.vn.wecare.feature.exercises.workout_dashboard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.dashboard.viewmodel.NutritionDashboardUiState
import com.vn.wecare.feature.home.HomeUiState
import com.vn.wecare.feature.home.HomeViewModel
import com.vn.wecare.feature.home.TrainingNow
import com.vn.wecare.feature.home.step_count.ui.compose.StepCountHomeCard
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.WeCareTypography
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.halfMidPadding
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.tinyPadding
import com.vn.wecare.utils.getProgressInFloatWithIntInput

//@Preview
//@Composable
//fun preview() {
//    ExerciseDashboardScreen(
//        onFootStepCountCardClick = {},
//        uiState = HomeUiState(),
//        onTrainingClick = {},
//        onWalkingIcClick = {},
//        onRunningIcClick = {},
//        onBicycleIcClick = {},
//        onMeditationIcClick = {},
//        onNavigateToExercise = {},
//        reportState = NutritionDashboardUiState(),
//        currentCalories = 100,
//        currentProtein = 100,
//        currentFat = 100,
//        currentCarbs = 100,
//        viewModel = ExerciseDashboardViewModel()
//    )
//}

@Composable
fun ExerciseDashboardScreen(
    modifier: Modifier = Modifier,
    onFootStepCountCardClick: () -> Unit,
    onTrainingClick: () -> Unit,
    onWalkingIcClick: () -> Unit,
    onRunningIcClick: () -> Unit,
    onBicycleIcClick: () -> Unit,
    onMeditationIcClick: () -> Unit,
    onNavigateToExercise: () -> Unit,
    uiState: HomeUiState,
    reportState: NutritionDashboardUiState,
    viewModel: ExerciseDashboardViewModel = hiltViewModel()
) {

    Scaffold(
        modifier = modifier
    ) {padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = normalPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = modifier.height(halfMidPadding))
            Text(
                text = "Let's workout",
                style = WeCareTypography.h2,
                color = Color.Black
            )
            Spacer(modifier = modifier.height(smallPadding))
            when (viewModel.getCaloPerDayResponse) {
                is Response.Loading -> {
                    CircularProgressIndicator()
                }
                is Response.Success -> {

                    val uiState = viewModel.caloPerDay.collectAsState().value
                    CaloOutOverview(
                        uiState = reportState,
                        currentCalories = uiState?.caloOut ?: 0,
                        currentProtein = uiState?.caloOutWalking ?: 0,
                        currentFat = uiState?.caloOutTraining ?: 0,
                        currentCarbs = uiState?.caloOutExercise ?: 0,
                    )
                }
                else -> {}
            }
            Spacer(modifier = modifier.height(normalPadding))
            TrainingNow(
                modifier = modifier,
                onTrainingClick,
                onWalkingIcClick,
                onRunningIcClick,
                onBicycleIcClick,
                onMeditationIcClick
            )
            Spacer(modifier = modifier.height(normalPadding))
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StepCountHomeCard(
                    onCardClick = onFootStepCountCardClick,
                    calories = uiState.caloriesBurnt,
                    steps = uiState.stepCount,
                    time = uiState.timeConsumed
                )
                ExerciseComponent(
                    onNavigateToExercise = onNavigateToExercise
                )
            }
            Spacer(modifier = modifier.height(normalPadding))
        }
    }
}

@Preview
@Composable
fun a() {
    ExerciseComponent(
        onNavigateToExercise = {}
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseComponent(
    modifier: Modifier = Modifier,
    onNavigateToExercise: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(210.dp),
        elevation = smallElevation,
        shape = Shapes.medium,
        onClick = onNavigateToExercise
    ) {
        Column(
            modifier = modifier
                .padding(halfMidPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier.padding(bottom = smallPadding)
                    .weight(6f),
                contentScale = ContentScale.Fit,
                painter = painterResource(id = R.drawable.trainer) , contentDescription = "" )
            Text(
                text = "Start Your Training \nJourney Today!",
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.opensans_semi_bold)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                ),
                color = Color.Black,
                modifier = modifier.wrapContentHeight().weight(3f)
            )
            Text(
                text = "Tap here",
                modifier = modifier.wrapContentHeight().weight(1f),
                style = WeCareTypography.caption.copy(fontSize = 10.sp)
            )
        }
    }
}

@Composable
fun CaloOutOverview(
    modifier: Modifier = Modifier,
    uiState: NutritionDashboardUiState,
    currentCalories: Int,
    currentProtein: Int,
    currentFat: Int,
    currentCarbs: Int,
) {

    val progressAnimationValue by animateFloatAsState(
        targetValue = getProgressInFloatWithIntInput(
            currentCalories, uiState.targetCaloriesAmount
        ), animationSpec = tween(1000)
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        shape = Shapes.medium,
        elevation = smallElevation
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(normalPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = modifier.weight(1.2f), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = modifier.size(175.dp),
                    progress = progressAnimationValue,
                    color = MaterialTheme.colors.primary,
                    strokeWidth = 10.dp,
                    backgroundColor = MaterialTheme.colors.secondary.copy(0.4f),
                    strokeCap = StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$currentCalories", style = MaterialTheme.typography.h1
                    )
                    Text(
                        text = "Goal: ${uiState.targetCaloriesAmount} cal",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(start = mediumPadding),
                horizontalAlignment = Alignment.Start
            ) {
                CaloOutOverviewItem(
                    modifier = modifier,
                    title = "Steps",
                    index = currentProtein,
                    target = uiState.targetProteinIndex,
                    color = Red400
                )
                Spacer(modifier = modifier.height(smallPadding))
                CaloOutOverviewItem(
                    modifier = modifier,
                    title = "Exercise",
                    index = currentFat,
                    target = uiState.targetFatIndex,
                    color = Yellow
                )
                Spacer(modifier = modifier.height(smallPadding))
                CaloOutOverviewItem(
                    modifier = modifier,
                    title = "Training",
                    index = currentCarbs,
                    target = uiState.targetCarbsIndex,
                    color = Blue
                )
            }
        }
    }
}

@Composable
private fun CaloOutOverviewItem(
    modifier: Modifier, title: String, index: Int, target: Int, color: Color
) {

    val animatedProgress = animateFloatAsState(
        targetValue = getProgressInFloatWithIntInput(index, target),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value

    Column(horizontalAlignment = Alignment.Start) {
        Text(text = title, style = MaterialTheme.typography.body1)
        Spacer(modifier = modifier.height(4.dp))
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(6.dp),
            color = color,
            progress = animatedProgress,
            strokeCap = StrokeCap.Round
        )
        Spacer(modifier = modifier.height(4.dp))
        Text(text = "${index}/$target cal", style = MaterialTheme.typography.caption)
    }
}