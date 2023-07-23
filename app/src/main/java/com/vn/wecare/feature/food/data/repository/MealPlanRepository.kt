package com.vn.wecare.feature.food.data.repository

import com.vn.wecare.feature.food.data.datasource.MealPlanDataSource
import com.vn.wecare.feature.food.data.model.Recipe.RecipeInformation
import javax.inject.Inject

class MealPlanRepository @Inject constructor(
    private val dataSource: MealPlanDataSource
) {
    fun getMealPlanWithCalories(targetCalories: Int) =
        dataSource.getMealPlanWithTargetCalories(targetCalories)

    fun getRecipeInformationWithId(id: Long) = dataSource.getRecipeInformationWithId(id)

    fun insertMealPlanToFirestore(dayId: String, recipe: RecipeInformation) =
        dataSource.insertMealPlanToFirestore(dayId, recipe)

    fun getMealPlanFromFirestore(dayId: String) = dataSource.getMealPlanFromFirestore(dayId)
}