package com.vn.wecare.feature.food.data.repository

import com.vn.wecare.feature.food.data.datasource.MealPlanDataSource
import javax.inject.Inject

class MealPlanRepository @Inject constructor(
    private val dataSource: MealPlanDataSource
) {
    fun getMealPlanWithCalories(targetCalories: Int) =
        dataSource.getMealPlanWithTargetCalories(targetCalories)
}