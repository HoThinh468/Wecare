package com.vn.wecare.core.data

import androidx.room.TypeConverter
import java.util.Calendar

class DateTimeConverters {

    @TypeConverter
    fun fromTimestamp(value: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = value
        return calendar
    }

    @TypeConverter
    fun dateToTimestamp(calendar: Calendar): Long {
        return calendar.timeInMillis
    }
}