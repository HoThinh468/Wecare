package com.vn.wecare.feature.home.step_count.data.model

import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity

data class StepsPerHour(
    val hourId: String,
    val dayId: String,
    val steps: Int,
    val calories: Int,
    val moveTime: Int
)

fun StepsPerHour.toEntity() = StepsPerHourEntity(
    this.hourId,
    this.dayId,
    this.steps,
    this.calories,
    this.moveTime
)