package com.vn.wecare.feature.home.step_count.di

import android.content.Context
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.feature.home.step_count.alarm.StepCountExactAlarms
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StepCountExactAlarmModule {

    @Provides
    @Singleton
    fun provideStepCountExactAlarm(
        @ApplicationContext context: Context
    ): ExactAlarms = StepCountExactAlarms(context)
}