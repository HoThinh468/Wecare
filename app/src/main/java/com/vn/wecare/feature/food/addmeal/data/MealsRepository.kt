package com.vn.wecare.feature.food.addmeal.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import javax.inject.Inject

class MealsRepository @Inject constructor(
    private val mealsApiService: MealsApiService
) {
    fun getMealsByNutrients(
        maxCalories: Int, minCalories: Int, maxProtein: Int, maxFat: Int, maxCarbs: Int
    ) = Pager(config = PagingConfig(
        pageSize = NUMBER_OF_MEALS_EACH_LOAD
    ), pagingSourceFactory = {
        MealsPagingSource(
            maxCalories, minCalories, maxProtein, maxFat, maxCarbs, mealsApiService
        )
    }).flow
}