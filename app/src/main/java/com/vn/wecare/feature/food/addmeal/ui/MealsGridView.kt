package com.vn.wecare.feature.food.addmeal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vn.wecare.feature.food.addmeal.viewmodel.AddMealViewModel
import com.vn.wecare.feature.food.data.model.MealRecipe
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.ui.theme.normalPadding
import java.util.Calendar

const val DEFAULT_ERROR_MESSAGE = "Oops, something went wrong, please try again later!"

@Composable
fun MealsGridView(
    modifier: Modifier,
    mealList: List<MealRecipe>,
    addMealViewModel: AddMealViewModel,
    mealTypeKey: MealTypeKey,
    openBottomSheet: () -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(normalPadding),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(normalPadding)
    ) {
        items(mealList.size) { index ->
            val item = mealList[index]
            FoodCardItemForGridView(
                modifier = modifier,
                meal = item,
                onCardClick = {
                    addMealViewModel.updateCurrentChosenMeal(item)
                    openBottomSheet()
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
}