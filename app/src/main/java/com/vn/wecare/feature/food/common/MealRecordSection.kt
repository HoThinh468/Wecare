package com.vn.wecare.feature.food.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vn.wecare.core.checkInternetConnection
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.model.MealRecordModel
import com.vn.wecare.ui.theme.xxxExtraPadding

@Composable
fun MealRecordSection(
    modifier: Modifier,
    mealUiState: MealUiState,
    navigateToDetailScreen: (mealRecordModel: MealRecordModel) -> Unit,
    deleteMealRecord: (mealRecord: MealRecordModel) -> Unit,
    onMinusClick: (mealRecord: MealRecordModel) -> Unit,
    onAddClick: (mealRecord: MealRecordModel) -> Unit
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        text = "Meal records",
        style = MaterialTheme.typography.h4
    )

    val isNetworkAvailable = checkInternetConnection(LocalContext.current)

    if (isNetworkAvailable) {
        mealUiState.getMealsResponse.let {
            when (it) {
                is Response.Loading -> {
                    CircularProgressIndicator(
                        modifier = modifier.padding(vertical = xxxExtraPadding),
                        color = MaterialTheme.colors.primary
                    )
                }

                is Response.Success -> {
                    if (mealUiState.mealRecords.isNotEmpty()) {
                        LazyColumn {
                            items(mealUiState.mealRecords.size) { i ->
                                val item = mealUiState.mealRecords[i]
                                MealRecordItem(
                                    modifier = modifier,
                                    mealRecord = item,
                                    navigateToDetailScreen = {
                                        navigateToDetailScreen(item)
                                    },
                                    isEditEnable = mealUiState.isAddMealEnable,
                                    deleteMealRecord = deleteMealRecord,
                                    onMinusClick = onMinusClick,
                                    onAddClick = onAddClick
                                )
                            }
                        }
                    } else {
                        NoMealRecordMessage(modifier = modifier)
                    }
                }

                is Response.Error -> {
                    FoodErrorMessage(modifier = modifier)
                }

                else -> { // Do nothing
                }
            }
        }
    } else {
        NoInternetMessage(modifier = modifier)
    }
}