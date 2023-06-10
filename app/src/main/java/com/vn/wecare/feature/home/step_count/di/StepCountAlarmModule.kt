package com.vn.wecare.feature.home.step_count.di

import android.content.Context
import com.vn.wecare.core.WecareSharePreferences
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.feature.home.step_count.alarm.StepCountExactAlarms
import com.vn.wecare.feature.home.step_count.alarm.StepCountInExactAlarms
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StepCountAlarmModule {

    @Provides
    @Singleton
    fun provideStepCountExactAlarm(
        @ApplicationContext context: Context, sharedPref: WecareSharePreferences
    ): ExactAlarms = StepCountExactAlarms(context, sharedPref)

    @Provides
    @Singleton
    fun provideStepCountInExactAlarm(
        @ApplicationContext context: Context, sharedPref: WecareSharePreferences
    ): InExactAlarms = StepCountInExactAlarms(context, sharedPref)
}