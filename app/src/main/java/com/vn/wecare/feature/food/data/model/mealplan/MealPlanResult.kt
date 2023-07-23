package com.vn.wecare.feature.food.data.model.mealplan

import com.squareup.moshi.Json

data class MealPlanResult(
    @Json(name = "meals") val meals: List<MealPlan> = emptyList(),
)
