package com.vn.wecare.feature.goal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goals(
    @PrimaryKey val userId: String = "",
    val stepsGoal: Int = 6000,
    val caloriesBurnedGoal: Int = (6000 * 0.04).toInt(),
    val moveTimeGoal: Int = (6000 * 0.01).toInt()
)