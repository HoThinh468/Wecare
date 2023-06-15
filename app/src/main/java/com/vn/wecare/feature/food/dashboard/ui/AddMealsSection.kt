package com.vn.wecare.feature.food.dashboard.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vn.wecare.R
import com.vn.wecare.feature.food.dashboard.viewmodel.NutritionDashboardUiState
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.mediumRadius
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.utils.getProgressInFloatWithIntInput

@Composable
fun AddMealsSection(
    modifier: Modifier,
    moveToBreakfastScreen: () -> Unit,
    moveToLunchScreen: () -> Unit,
    moveToSnackScreen: () -> Unit,
    moveToDinnerScreen: () -> Unit,
    moveToAddYourOwnMealsScreen: () -> Unit,
    moveToAddMealScreen: (index: Int) -> Unit,
    uiState: NutritionDashboardUiState
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Add your own meals", style = MaterialTheme.typography.h4
        )
        TextButton(onClick = { moveToAddYourOwnMealsScreen() }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Text(text = "Add", style = MaterialTheme.typography.button)
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