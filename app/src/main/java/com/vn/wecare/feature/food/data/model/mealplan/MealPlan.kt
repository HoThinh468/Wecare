package com.vn.wecare.feature.food.data.model.mealplan

import com.squareup.moshi.Json

data class MealPlan(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "readyInMinutes") val readyInMinutes: Int,
    @Json(name = "image") val image: String?,
    @Json(name = "imageUrls") val imageUrls: List<String>?
)