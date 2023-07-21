package com.vn.wecare.feature.food.data.repository

import com.vn.wecare.feature.food.data.datasource.MealRecipeDataSource
import com.vn.wecare.feature.food.data.model.MealByName
import com.vn.wecare.feature.food.data.model.MealTypeKey
import com.vn.wecare.feature.food.data.model.toMealRecipe
import javax.inject.Inject

class MealsRecipeRepository @Inject constructor(
    private val mealRecipeDataSource: MealRecipeDataSource
) {
    fun insertMealRecipeToFirestore(mealByNames: List<MealByName>) {
        for (meal in mealByNames) {
            mealRecipeDataSource.insertMealRecipeToFirestore(meal.toMealRecipe())
        }
    }

    fun getMealsRecipeWithMealTypeKey(mealTypeKey: MealTypeKey) =
        mealRecipeDataSource.getMealRecipeWithMealTypeKey(mealTypeKey)
}