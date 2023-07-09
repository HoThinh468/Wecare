package com.vn.wecare.feature.food.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.food.mealdetail.NutrientIndexItem
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.mediumElevation
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallPadding

@Composable
fun FoodNutrientsReport(
    modifier: Modifier, uiState: NutrientReportUiState
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = mediumElevation,
        shape = Shapes.large,
    ) {
        Column(
            modifier = modifier.padding(midPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Total Nutrients detail",
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = modifier.height(normalPadding))
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NutrientIndexItem(
                    modifier = modifier,
                    title = "PROTEIN",
                    index = "${uiState.proteinAmount}g",
                    color = Red400
                )
                NutrientIndexItem(
                    modifier = modifier,
                    title = "FAT",
                    index = "${uiState.fatAmount}g",
                    color = Yellow
                )
                NutrientIndexItem(
                    modifier = modifier,
                    title = "CARBS",
                    index = "${uiState.carbsAmount}g",
                    color = Blue
                )
            }
            Spacer(modifier = modifier.height(midPadding))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = "Average calories by categories",
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = modifier.height(normalPadding))
            NutrientsIndexProgress(
                modifier = modifier,
                title = "Breakfast",
                index = uiState.breakfastCalories,
                target = uiState.breakfastTargetCalories,
                unit = "cal",
                progress = uiState.breakfastProgress
            )
            Spacer(modifier = modifier.height(normalPadding))
            NutrientsIndexProgress(
                modifier = modifier,
                title = "Lunch",
                index = uiState.lunchCalories,
                target = uiState.lunchTargetCalories,
                unit = "cal",
                progress = uiState.lunchProgress
            )
            Spacer(modifier = modifier.height(normalPadding))
            NutrientsIndexProgress(
                modifier = modifier,
                title = "Snack",
                index = uiState.snackCalories,
                target = uiState.snackTargetCalories,
                unit = "cal",
                progress = uiState.snackProgress
            )
            Spacer(modifier = modifier.height(normalPadding))
            NutrientsIndexProgress(
                modifier = modifier,
                title = "Dinner",
                index = uiState.dinnerCalories,
                target = uiState.dinnerTargetCalories,
                unit = "cal",
                progress = uiState.dinnerProgress
            )
        }
    }
}

@Composable
fun NutrientsIndexProgress(
    modifier: Modifier,
    title: String,
    index: Int,
    target: Int,
    unit: String,
    color: Color = MaterialTheme.colors.primary,
    progress: Float
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .padding(bottom = smallPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "$title (${index}/$target ${unit})", style = MaterialTheme.typography.body2)
            Text(text = "${(progress * 100).toInt()}%", style = MaterialTheme.typography.body2)
        }
        LinearProgressIndicator(
            modifier = modifier
                .fillMaxWidth()
                .height(6.dp),
            strokeCap = StrokeCap.Round,
            color = color,
            progress = progress
        )
    }
}