package com.vn.wecare.core.di

import android.content.Context
import androidx.room.Room
import com.vn.wecare.core.data.WecareDatabase
import com.vn.wecare.feature.account.data.dao.UserDao
import com.vn.wecare.feature.home.step_count.data.dao.StepsPerDayDao
import com.vn.wecare.feature.home.step_count.data.dao.StepsPerHourDao
import com.vn.wecare.feature.home.water.tracker.data.dao.WaterRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val DATABASE_NAME = "wecare-db"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WecareDatabase {
        return Room.databaseBuilder(
            context, WecareDatabase::class.java, DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideStepsPerHourDao(database: WecareDatabase): StepsPerHourDao {
        return database.stepsPerHourDao()
    }

    @Provides
    fun provideStepsPerDayDao(database: WecareDatabase): StepsPerDayDao {
        return database.stepsPerDayDao()
    }

    @Provides
    fun provideUserDao(database: WecareDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideWaterRecordDao(database: WecareDatabase): WaterRecordDao {
        return database.waterRecordDao()
    }
}