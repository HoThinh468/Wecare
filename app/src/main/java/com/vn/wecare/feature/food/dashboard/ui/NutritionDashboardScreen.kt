package com.vn.wecare.feature.food.dashboard.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.dashboard.viewmodel.NutritionDashboardViewmodel
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.xxxExtraPadding
import com.vn.wecare.utils.common_composable.LoadingDialog

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NutritionDashboardScreen(
    modifier: Modifier = Modifier,
    moveToBreakfastScreen: () -> Unit,
    moveToLunchScreen: () -> Unit,
    moveToSnackScreen: () -> Unit,
    moveToDinnerScreen: () -> Unit,
    moveToAddMealScreen: (index: Int) -> Unit,
    moveToAddYourOwnMealsListScreen: () -> Unit,
    moveToSearchFoodScreen: () -> Unit,
    moveToReportScreen: () -> Unit,
    moveToMealPlan: () -> Unit,
    nutritionDashboardViewmodel: NutritionDashboardViewmodel
) {
    val uiState = nutritionDashboardViewmodel.uiState.collectAsState()

    uiState.value.updateState.let {
        when (it) {
            is Response.Loading -> {
                LoadingDialog(loading = it == Response.Loading) {}
            }

            is Response.Success -> {
                Toast.makeText(LocalContext.current, "Update successfully!", Toast.LENGTH_SHORT)
                    .show()
            }

            is Response.Error -> {
                Toast.makeText(LocalContext.current, "Update fail!", Toast.LENGTH_SHORT).show()
            }

            else -> { // do nothing
            }
        }
    }

    Scaffold(modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            NutritionAppbar(
                modifier = modifier,
                dateTime = uiState.value.dateTime,
                moveToSearchFoodScreen = moveToSearchFoodScreen,
                moveToReportScreen = moveToReportScreen
            )
        }) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = midPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = modifier.height(4.dp))
            NutritionOverviewSection(
                modifier = modifier,
                uiState = uiState.value,
                currentCalories = nutritionDashboardViewmodel.totalCalories,
                currentProtein = nutritionDashboardViewmodel.totalProtein,
                currentFat = nutritionDashboardViewmodel.totalFat,
                currentCarbs = nutritionDashboardViewmodel.totalCarbs
            )
            Spacer(modifier = modifier.height(midPadding))
            JustForYouSection(
                modifier = modifier,
                navigateToAddYourOwnMealScreen = {
                    moveToAddYourOwnMealsListScreen()
                },
                moveToMealPlanScreen = { moveToMealPlan() },
            )
            Spacer(modifier = modifier.height(midPadding))
            AddMealsSection(
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
private fun NutritionAppbar(
    modifier: Modifier,
    dateTime: String,
    moveToSearchFoodScreen: () -> Unit,
    moveToReportScreen: () -> Unit
) {
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
                    text = dateTime, style = MaterialTheme.typography.h3
                )
            }
            Row {
                IconButton(onClick = moveToSearchFoodScreen) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
                IconButton(onClick = moveToReportScreen) {
                    Icon(imageVector = Icons.Default.BarChart, contentDescription = null)
                }
            }
        }
    }
}