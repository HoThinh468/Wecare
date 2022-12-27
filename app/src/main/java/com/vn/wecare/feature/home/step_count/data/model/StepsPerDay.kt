package com.vn.wecare.feature.home.step_count.data.model

import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity

data class StepsPerDay(
    val dayId: String,
    val userId: String,
    val steps: Int,
    val calories: Int,
    val moveTime: Int,
)

fun StepsPerDay.toEntity() = StepsPerDayEntity(
    this.dayId, this.userId, this.steps, this.calories, this.moveTime
)