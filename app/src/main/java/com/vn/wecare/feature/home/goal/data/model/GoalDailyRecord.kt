package com.vn.wecare.feature.home.goal.data.model

data class GoalDailyRecord(
    val day: String = "",
    val dayInLong: Long = 0,
    val caloriesIn: Int = 0,
    val caloriesOut: Int = 0,
    val proteinAmount: Int = 0,
    val fatAmount: Int = 0,
    val carbsAmount: Int = 0,
    val goalDailyCalories: Int = 0,
    val goalDailyCaloriesOut: Int = 0
)