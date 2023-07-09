package com.vn.wecare.feature.food.dashboard.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.vn.wecare.feature.food.dashboard.viewmodel.NutritionDashboardUiState
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Red400
import com.vn.wecare.ui.theme.Shapes
import com.vn.wecare.ui.theme.Yellow
import com.vn.wecare.ui.theme.mediumPadding
import com.vn.wecare.ui.theme.normalPadding
import com.vn.wecare.ui.theme.smallElevation
import com.vn.wecare.ui.theme.smallPadding
import com.vn.wecare.utils.getProgressInFloatWithIntInput

@Composable
fun NutritionOverviewSection(
    modifier: Modifier,
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
                NutritionOverviewItem(
                    modifier = modifier,
                    title = "Protein",
                    index = currentProtein,
                    target = uiState.targetProteinIndex,
                    color = Red400
                )
                Spacer(modifier = modifier.height(smallPadding))
                NutritionOverviewItem(
                    modifier = modifier,
                    title = "Fat",
                    index = currentFat,
                    target = uiState.targetFatIndex,
                    color = Yellow
                )
                Spacer(modifier = modifier.height(smallPadding))
                NutritionOverviewItem(
                    modifier = modifier,
                    title = "Carbs",
                    index = currentCarbs,
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
            color = color,
            progress = animatedProgress,
            strokeCap = StrokeCap.Round
        )
        Spacer(modifier = modifier.height(4.dp))
        Text(text = "${index}/$target g", style = MaterialTheme.typography.caption)
    }
}