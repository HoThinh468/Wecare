package com.vn.wecare.feature.food.common

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.food.data.model.MealRecordModel

data class MealUiState(
    val dateTime: String = "",
    val calories: Int = 0,
    val targetCalories: Int = 0,
    val protein: Int = 0,
    val targetProtein: Int = 0,
    val fat: Int = 0,
    val targetFat: Int = 0,
    val carbs: Int = 0,
    val targetCarbs: Int = 0,
    val getMealsResponse: Response<Boolean>? = null,
    val mealRecords: List<MealRecordModel> = emptyList(),
    val updateMealRecordResponse: Response<Boolean>? = null,
    val isAddMealEnable: Boolean = true,
)