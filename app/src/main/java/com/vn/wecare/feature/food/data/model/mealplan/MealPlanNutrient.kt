package com.vn.wecare.feature.food.data.model.mealplan

import com.squareup.moshi.Json

data class MealPlanNutrient(
    @Json(name = "calories") val calories: Float = 0f,
    @Json(name = "protein") val protein: Float = 0f,
    @Json(name = "fat") val fat: Float = 0f,
    @Json(name = "carbs") val carbs: Float = 0f,
)
