package com.vn.wecare.feature.goal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goals(
    @PrimaryKey val userId: String = "",
    val stepsGoal: Int = 0,
    val caloriesBurnedGoal: Int = 0,
    val moveTimeGoal: Int = 0
)