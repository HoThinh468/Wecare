package com.vn.wecare.core.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vn.wecare.feature.account.data.dao.UserDao
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.feature.food.data.dao.MealByNutrientsDao
import com.vn.wecare.feature.food.data.model.MealByNutrientsEntity
import com.vn.wecare.feature.home.step_count.data.dao.StepsPerDayDao
import com.vn.wecare.feature.home.step_count.data.dao.StepsPerHourDao
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import com.vn.wecare.feature.home.water.data.dao.WaterRecordDao
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity

@Database(
    entities = [StepsPerDayEntity::class, StepsPerHourEntity::class, WecareUser::class, WaterRecordEntity::class, MealByNutrientsEntity::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 3, to = 4)]
)
@TypeConverters(DateTimeConverters::class)
abstract class WecareDatabase : RoomDatabase() {
    abstract fun stepsPerDayDao(): StepsPerDayDao
    abstract fun stepsPerHourDao(): StepsPerHourDao
    abstract fun userDao(): UserDao
    abstract fun waterRecordDao(): WaterRecordDao
    abstract fun mealByNutrientsDao(): MealByNutrientsDao
}