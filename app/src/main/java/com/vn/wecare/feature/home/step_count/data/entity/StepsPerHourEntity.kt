package com.vn.wecare.feature.home.step_count.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour

@Entity(tableName = "steps_per_hour")
data class StepsPerHourEntity(
    @PrimaryKey val hourId: String,
    val dayIncludeId: String,
    val steps: Int,
    val calories: Int,
    val moveTime: Int,
)

fun StepsPerHourEntity.toModel() = StepsPerHour(
    this.hourId,
    this.dayIncludeId,
    this.steps,
    this.calories,
    this.moveTime
)