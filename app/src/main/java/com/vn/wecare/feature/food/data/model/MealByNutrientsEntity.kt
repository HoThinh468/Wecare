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
