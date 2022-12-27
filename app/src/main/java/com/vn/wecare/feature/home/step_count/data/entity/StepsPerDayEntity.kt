package com.vn.wecare.feature.home.step_count.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay

@Entity(tableName = "steps_per_day")
data class StepsPerDayEntity(
    @PrimaryKey val dayId: String,
    val userId: String,
    val steps: Int,
    val calories: Int,
    val moveTime: Int,
)

fun StepsPerDayEntity.toModel() = StepsPerDay(
    this.dayId, this.userId, this.steps, this.calories, this.moveTime
)