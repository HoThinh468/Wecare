package com.vn.wecare.feature.food.data.model

import com.vn.wecare.feature.food.data.model.Recipe.Instruction

data class MealRecipe(
    val id: Long = 0,
    val title: String = "",
    val imgUrl: String = "",
    val cookingMinutes: Int = 0,
    val servings: Int = 0,
    val pricePerServing: Float = 0f,
    val summary: String = "",
    val dishTypes: List<String> = emptyList(),
    val diets: List<String> = emptyList(),
    val instructions: List<Instruction> = emptyList(),
    val calories: Int = 0,
    val protein: String = "0g",
    val fat: String = "0g",
    val carbs: String = "0g"
)

fun MealRecipe.toMealRecordModel(): MealRecordModel {
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
