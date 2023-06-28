package com.vn.wecare.feature.food.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_by_nutrients")
data class MealByNutrientsEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val imgUrl: String,
    val imageType: String,
    val calories: Int,
    val protein: String,
    val fat: String,
    val carbs: String
)

fun MealByNutrientsEntity.toMealByNutrients(): MealByNutrients {
    return MealByNutrients(
        id = this.id,
        title = this.title,
        imgUrl = this.imgUrl,
        imageType = this.imageType,
        calories = this.calories,
        protein = this.protein,
        fat = this.fat,
        carbs = this.carbs
    )
}
