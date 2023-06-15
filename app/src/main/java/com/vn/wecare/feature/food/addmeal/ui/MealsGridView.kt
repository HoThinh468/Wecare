package com.vn.wecare.feature.food.addmeal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.vn.wecare.feature.food.addmeal.viewmodel.AddMealViewModel
import com.vn.wecare.feature.food.data.model.MealByNutrients
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.ui.theme.midPadding
import com.vn.wecare.ui.theme.normalPadding
import java.util.Calendar

const val DEFAULT_ERROR_MESSAGE = "Oops, something went wrong, please try again later!"

@Composable
fun MealsGridView(
    modifier: Modifier,
    mealList: LazyPagingItems<MealByNutrients>,
    addMealViewModel: AddMealViewModel,
    mealTypeKey: MealTypeKey,
    openBottomSheet: () -> Unit,
) {
    when (mealList.loadState.refresh) {
        is LoadState.Error -> {
            val message = (mealList.loadState.refresh as LoadState.Error).error.message
            LoadingDataErrorUI(modifier = modifier, message ?: DEFAULT_ERROR_MESSAGE)
        }

        is LoadState.Loading -> {
            CircularProgressIndicator(
                modifier = modifier.padding(midPadding), color = MaterialTheme.colors.primary
            )
        }

        else -> {
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxSize()
                    .padding(normalPadding),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(normalPadding)
            ) {
                items(mealList.itemCount) { index ->
                    val item = mealList[index]
                    if (item != null) {
                        FoodCardItemForGridView(
                            modifier = modifier,
                            meal = item,
                            onCardClick = {
                                openBottomSheet()
                                addMealViewModel.updateCurrentChosenMeal(item)
                            },
                            onAddMealClick = {
                                addMealViewModel.insertMealRecord(
                                    dateTime = Calendar.getInstance(),
                                    mealTypeKey = mealTypeKey,
                                    meal = item
                                )
                            },
                        )
                    }
                }

                item {
                    if (mealList.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = modifier.padding(midPadding),
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}