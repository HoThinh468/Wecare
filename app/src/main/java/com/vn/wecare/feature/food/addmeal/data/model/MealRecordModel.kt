package com.vn.wecare.feature.food.addmeal.data.model

data class MealRecordModel(
    val id: Long = 0,
    val title: String = "",
    val imgUrl: String = "",
    val calories: Int = 0,
    val protein: String = "0g",
    val fat: String = "0g",
    val carbs: String = "0g",
    val quantity: Int = 0
)