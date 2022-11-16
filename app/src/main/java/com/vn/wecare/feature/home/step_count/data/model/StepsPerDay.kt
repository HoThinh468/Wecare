package com.vn.wecare.feature.home.step_count.data.model

import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity

data class StepsPerDay(
    val dayId: String,
    val userId: String
)

fun StepsPerDay.toEntity() = StepsPerDayEntity(this.dayId, this.userId)