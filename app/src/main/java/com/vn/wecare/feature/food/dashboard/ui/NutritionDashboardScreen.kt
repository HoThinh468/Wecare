package com.vn.wecare.feature.food.dashboard.ui

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.food.dashboard.viewmodel.NutritionDashboardUiState
import com.vn.wecare.feature.food.dashboard.viewmodel.NutritionDashboardViewmodel
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.ui.theme.xxxExtraPadding
import com.vn.wecare.utils.getProgressInFloatWithIntInput

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NutritionDashboardScreen(
    modifier: Modifier = Modifier,
    moveToBreakfastScreen: () -> Unit,
    moveToLunchScreen: () -> Unit,
    moveToSnackScreen: () -> Unit,
    moveToDinnerScreen: () -> Unit,
    moveToAddMealScreen: (index: Int) -> Unit,
    nutritionDashboardViewmodel: NutritionDashboardViewmodel
) {
    val uiState = nutritionDashboardViewmodel.uiState.collectAsState()

    Scaffold(modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = { NutritionAppbar(modifier = modifier) }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = midPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = modifier.height(4.dp))
            NutritionOverview(modifier = modifier, uiState = uiState.value)
            Spacer(modifier = modifier.height(normalPadding))
            AddMeals(
                modifier = modifier,
                moveToBreakfastScreen = moveToBreakfastScreen,
                moveToLunchScreen = moveToLunchScreen,
                moveToSnackScreen = moveToSnackScreen,
                moveToDinnerScreen = moveToDinnerScreen,
                moveToAddMealScreen = moveToAddMealScreen,
                uiState = uiState.value
            )
            Spacer(modifier = modifier.height(xxxExtraPadding))
        }
    }
}

@Composable
private fun NutritionAppbar(modifier: Modifier) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(midPadding)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Today", style = MaterialTheme.typography.body2
                )
                Text(
                    text = "Wed, 1 May", style = MaterialTheme.typography.h3
                )
            }
            Image(
                modifier = modifier.height(64.dp),
                painter = painterResource(id = R.drawable.img_hamburger),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun NutritionOverview(
    modifier: Modifier, uiState: NutritionDashboardUiState
) {

    val progressAnimationValue by animateFloatAsState(
        targetValue = getProgressInFloatWithIntInput(
            uiState.currentCaloriesAmount, uiState.targetCaloriesAmount
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
                    progress = 1f,
                    color = MaterialTheme.colors.secondary.copy(0.4f),
                    strokeWidth = 10.dp,
                )
                CircularProgressIndicator(
                    modifier = modifier.size(175.dp),
                    progress = progressAnimationValue,
                    color = MaterialTheme.colors.primary,
                    strokeWidth = 10.dp,
                    strokeCap = StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${uiState.currentCaloriesAmount}",
                        style = MaterialTheme.typography.h1
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
                NutritionOverviewItem(
                    modifier = modifier,
                    title = "Protein",
                    index = uiState.currentProteinIndex,
                    target = uiState.targetProteinIndex,
                    color = Red400
                )
                Spacer(modifier = modifier.height(smallPadding))
                NutritionOverviewItem(
                    modifier = modifier,
                    title = "Fat",
                    index = uiState.currentFatIndex,
                    target = uiState.targetFatIndex,
                    color = Yellow
                )
                Spacer(modifier = modifier.height(smallPadding))
                NutritionOverviewItem(
                    modifier = modifier,
                    title = "Carbs",
                    index = uiState.currentCarbsIndex,
                    target = uiState.targetCarbsIndex,
                    color = Blue
                )
            }
        }
    }
}

@Composable
private fun NutritionOverviewItem(
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
            backgroundColor = MaterialTheme.colors.secondary.copy(0.4f),
            color = color,
            progress = animatedProgress,
            strokeCap = StrokeCap.Round
        )
        Spacer(modifier = modifier.height(4.dp))
        Text(text = "${index}/$target g", style = MaterialTheme.typography.caption)
    }
}

@Composable
private fun AddMeals(
    modifier: Modifier,
    moveToBreakfastScreen: () -> Unit,
    moveToLunchScreen: () -> Unit,
    moveToSnackScreen: () -> Unit,
    moveToDinnerScreen: () -> Unit,
    moveToAddMealScreen: (index: Int) -> Unit,
    uiState: NutritionDashboardUiState
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Add your meals", style = MaterialTheme.typography.h4
        )
        IconButton(onClick = { moveToAddMealScreen(0) }) {
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
        }
    }
    AddYourMealItem(modifier = modifier,
        mealName = "Breakfast",
        currentCal = uiState.breakfastCurrentCalories,
        targetCal = uiState.breakfastTargetCalories,
        thumbnailIdRes = R.drawable.img_breakfast,
        onCardClick = moveToBreakfastScreen,
        onAddBtnClick = {
            moveToAddMealScreen(0)
        })
    Spacer(modifier = modifier.height(normalPadding))
    AddYourMealItem(modifier = modifier,
        mealName = "Lunch",
        currentCal = uiState.lunchCurrentCalories,
        targetCal = uiState.lunchTargetCalories,
        thumbnailIdRes = R.drawable.img_lunch,
        onCardClick = moveToLunchScreen,
        onAddBtnClick = {
            moveToAddMealScreen(1)
        })
    Spacer(modifier = modifier.height(normalPadding))
    AddYourMealItem(modifier = modifier,
        mealName = "Snack",
        currentCal = uiState.snackCurrentCalories,
        targetCal = uiState.snackTargetCalories,
        thumbnailIdRes = R.drawable.img_snack,
        onCardClick = moveToSnackScreen,
        onAddBtnClick = {
            moveToAddMealScreen(2)
        })
    Spacer(modifier = modifier.height(normalPadding))
    AddYourMealItem(modifier = modifier,
        mealName = "Dinner",
        currentCal = uiState.dinnerCurrentCalories,
        targetCal = uiState.dinnerTargetCalories,
        thumbnailIdRes = R.drawable.img_dinner,
        onCardClick = moveToDinnerScreen,
        onAddBtnClick = {
            moveToAddMealScreen(3)
        })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun AddYourMealItem(
    modifier: Modifier,
    mealName: String,
    currentCal: Int,
    targetCal: Int,
    @DrawableRes thumbnailIdRes: Int,
    onAddBtnClick: () -> Unit = {},
    onCardClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        shape = Shapes.medium,
        elevation = smallElevation,
        onClick = onCardClick
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(normalPadding)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = modifier.size(56.dp),
                        painter = painterResource(id = thumbnailIdRes),
                        contentDescription = null
                    )
                    Column(modifier = modifier.padding(start = normalPadding)) {
                        Text(mealName, style = MaterialTheme.typography.h3)
                        Text(
                            "${currentCal}/$targetCal cal", style = MaterialTheme.typography.body2
                        )
                    }
                }
                Button(onClick = { onAddBtnClick() }, shape = RoundedCornerShape(mediumRadius)) {
                    Icon(
                        painterResource(id = R.drawable.ic_add),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colors.onPrimary
                    )
                    Text("Add")
                }
            }
            Spacer(modifier = modifier.height(normalPadding))
            LinearProgressIndicator(
                modifier = modifier
                    .fillMaxWidth()
                    .height(4.dp),
                backgroundColor = MaterialTheme.colors.secondary.copy(0.4f),
                color = MaterialTheme.colors.primary,
                progress = getProgressInFloatWithIntInput(currentCal, targetCal),
                strokeCap = StrokeCap.Round
            )
        }
    }
}