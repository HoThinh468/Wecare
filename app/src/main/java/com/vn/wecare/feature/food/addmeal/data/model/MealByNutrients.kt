package com.vn.wecare.feature.food.addmeal.data.model

import com.squareup.moshi.Json

data class MealByNutrients(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "image") val imgUrl: String,
    @Json(name = "imageType") val imageType: String,
    @Json(name = "calories") val calories: Int,
    @Json(name = "protein") val protein: String,
    @Json(name = "fat") val fat: String,
    @Json(name = "carbs") val carbs: String
)

fun MealByNutrients.toModel(): MealRecordModel {
    return MealRecordModel(
        id = this.id,
        title = this.title,
        imgUrl = this.imgUrl,
        calories = this.calories,
        protein = this.protein,
        fat = this.fat,
        carbs = this.carbs
    )
}