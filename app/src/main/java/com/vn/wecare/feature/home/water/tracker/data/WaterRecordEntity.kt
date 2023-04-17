package com.vn.wecare.feature.home.water.tracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.vn.wecare.core.data.DateTimeConverters
import java.util.Date

@Entity(tableName = "water_record")
data class WaterRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val recordId: Int = 0,
    val amount: Int = 0,
    @TypeConverters(DateTimeConverters::class) val dateTime: Date?
)