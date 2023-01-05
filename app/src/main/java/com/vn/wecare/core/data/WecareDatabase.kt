package com.vn.wecare.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vn.wecare.feature.account.data.dao.UserDao
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.feature.home.step_count.data.dao.StepsPerDayDao
import com.vn.wecare.feature.home.step_count.data.dao.StepsPerHourDao
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity

@Database(
    entities = [StepsPerDayEntity::class, StepsPerHourEntity::class, WecareUser::class],
    version = 1,
    exportSchema = false
)
abstract class WecareDatabase : RoomDatabase() {
    abstract fun stepsPerDayDao(): StepsPerDayDao
    abstract fun stepsPerHourDao(): StepsPerHourDao
    abstract fun userDao(): UserDao
}