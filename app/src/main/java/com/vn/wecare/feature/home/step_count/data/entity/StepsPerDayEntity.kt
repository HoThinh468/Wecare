package com.vn.wecare.feature.home.step_count.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "steps_per_day",
)
data class StepsPerDayEntity(
    @PrimaryKey @ColumnInfo(name = "day_id") val DayId: String,
    @ColumnInfo(name = "user_id") val userId: String,
)