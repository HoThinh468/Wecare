package com.vn.wecare.feature.food.data.model

import com.squareup.moshi.Json
import com.vn.wecare.feature.food.data.model.Recipe.Instruction

data class MealByName(
    @Json(name = "id") val id: Long = 0,
    @Json(name = "title") val title: String = "",
    @Json(name = "image") val image: String = "",
    @Json(name = "servings") val servings: Int = 0,
    @Json(name = "nutrition") val nutrition: Nutrition = Nutrition(),
    @Json(name = "summary") val summary: String = "",
    @Json(name = "dishTypes") val dishTypes: List<String> = emptyList(),
    @Json(name = "diets") val diets: List<String> = emptyList(),
    @Json(name = "pricePerServing") val pricePerServing: Float = 0f,
    @Json(name = "cookingMinutes") val cookingMinutes: Int = 0,
    @Json(name = "analyzedInstructions") val instructions: List<Instruction> = emptyList(),
)

fun MealByName.toMealRecipe(): MealRecipe {
    return MealRecipe(
        id = this.id,
        title = this.title,
        imgUrl = this.image,
        cookingMinutes = this.cookingMinutes,
        servings = this.servings,
        pricePerServing = this.pricePerServing,
        summary = this.summary,
        dishTypes = this.dishTypes,
        diets = this.diets,
        instructions = this.instructions,
        calories = this.nutrition.nutrients[0].amount.toInt(),
        protein = "${this.nutrition.nutrients[1].amount.toInt()}${this.nutrition.nutrients[1].unit}",
        fat = "${this.nutrition.nutrients[2].amount.toInt()}${this.nutrition.nutrients[2].unit}",
        carbs = "${this.nutrition.nutrients[3].amount.toInt()}${this.nutrition.nutrients[3].unit}",
    )
}