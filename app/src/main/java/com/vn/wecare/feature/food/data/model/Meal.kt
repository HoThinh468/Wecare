package com.vn.wecare.feature.food.data.model

data class Meal(
    val id: Long = 0,
    val name: String = "",
    val imgUrl: String = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=580&q=80",
    val category: String = MealTypeKey.BREAKFAST.value,
    val calories: Int = 0,
    val protein: Int = 0,
    val fat: Int = 0,
    val carbs: Int = 0,
)

fun Meal.toMealByNutrients(): MealByNutrients = MealByNutrients(
    id = this.id,
    title = this.name,
    imgUrl = this.imgUrl,
    calories = this.calories,
    protein = "${this.protein}g",
    fat = "${this.fat}g",
    carbs = "${this.carbs}g",
    imageType = "jpg"
)