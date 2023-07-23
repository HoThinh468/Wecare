package com.vn.wecare.feature.food.data.model.Recipe

import com.squareup.moshi.Json
import com.vn.wecare.feature.food.data.model.MealRecipe
import com.vn.wecare.feature.food.data.model.Nutrition

data class RecipeInformation(
    @Json(name = "id") val id: Long = 0,
    @Json(name = "title") val title: String = "",
    @Json(name = "image") val image: String = "",
    @Json(name = "servings") val servings: Int = 0,
    @Json(name = "nutrition") val nutrition: Nutrition = Nutrition(),
    @Json(name = "dishTypes") val dishTypes: List<String> = emptyList(),
    @Json(name = "pricePerServing") val pricePerServing: Float = 0f,
    @Json(name = "readyInMinutes") val readyInMinutes: Int = 0,
    @Json(name = "analyzedInstructions") val instructions: List<Instruction> = emptyList(),
)

fun RecipeInformation.toMealRecipe(): MealRecipe {
    return MealRecipe(
        id = this.id,
        title = this.title,
        imgUrl = this.image,
        cookingMinutes = this.readyInMinutes,
        servings = this.servings,
        pricePerServing = this.pricePerServing,
        protein = if (this.nutrition.nutrients.isNotEmpty()) "${this.nutrition.nutrients[8].amount.toInt()}g" else "0g",
        calories = if (this.nutrition.nutrients.isNotEmpty()) this.nutrition.nutrients[0].amount.toInt() else 0,
        fat = if (this.nutrition.nutrients.isNotEmpty()) "${this.nutrition.nutrients[1].amount.toInt()}g" else "0g",
        carbs = if (this.nutrition.nutrients.isNotEmpty()) "${this.nutrition.nutrients[3].amount.toInt()}g" else "0g",
        instructions = this.instructions
    )
}
