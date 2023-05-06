package com.vn.wecare.feature.food.addmeal.data

import com.squareup.moshi.Json

data class MealsByNutrients(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "image") val imgUrl: String,
    @Json(name = "imageType") val imageType: String,
    @Json(name = "calories") val calories: Int,
    @Json(name = "protein") val protein: String,
    @Json(name = "carbs") val carbs: String
)

data class MealsInformation(
    private val id: Long,
    val title: String,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val dairyFree: Boolean,
    val gaps: String,
    val servings: Int,
    val healthScore: Int,
    val pricePerServing: Float,
    val readyInMinutes: Int,
    @Json(name = "image") val imgUrl: String,
    val imageType: String,
    val analyzedInstructions: String
)