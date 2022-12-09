package com.vn.wecare.core.di

import android.content.Context
import androidx.room.Room
import com.vn.wecare.core.data.WecareDatabase
import com.vn.wecare.feature.home.step_count.data.dao.StepsPerHourDao
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
    fun provideDatabase(@ApplicationContext context: Context): WecareDatabase {
        return Room.databaseBuilder(
            context, WecareDatabase::class.java, DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideStepsPerHourDao(database: WecareDatabase): StepsPerHourDao {
        return database.stepsPerHourDao()
    }
}