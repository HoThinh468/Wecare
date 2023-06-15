package com.vn.wecare.feature.food.data.model

import com.squareup.moshi.Json

data class MealByName(
    @Json(name = "id") val id: Long = 0,
    @Json(name = "title") val title: String = "",
    @Json(name = "image") val image: String = "",
    @Json(name = "nutrition") val nutrition: Nutrition = Nutrition()
)

fun MealByName.toMealByNutrients(): MealByNutrients {

    return if (this.nutrition.nutrients.isNotEmpty()) {
        MealByNutrients(
            id = this.id,
            title = this.title,
            imageType = "",
            imgUrl = this.image,
            calories = this.nutrition.nutrients[0].amount.toInt(),
            protein = "${this.nutrition.nutrients[1].amount.toInt()}${this.nutrition.nutrients[1].unit}",
            fat = "${this.nutrition.nutrients[2].amount.toInt()}${this.nutrition.nutrients[2].unit}",
            carbs = "${this.nutrition.nutrients[3].amount.toInt()}${this.nutrition.nutrients[3].unit}",
        )
    } else {
        MealByNutrients(
            id = this.id,
            title = this.title,
            imageType = "",
            imgUrl = this.image,
            calories = 0,
            protein = "0g",
            fat = "0g",
            carbs = "0g",
        )
    }
}