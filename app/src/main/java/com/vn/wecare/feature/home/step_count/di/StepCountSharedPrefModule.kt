package com.vn.wecare.feature.home.step_count.di

import android.content.Context
import android.content.SharedPreferences
import com.vn.wecare.feature.home.step_count.PREVIOUS_DAY_STEPS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class StepCountSharePref

@Module
@InstallIn(SingletonComponent::class)
object StepCountSharePrefModule {

    @Singleton
    @Provides
    @StepCountSharePref
    fun provideStepCountSharedPref(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PREVIOUS_DAY_STEPS, Context.MODE_PRIVATE)
}