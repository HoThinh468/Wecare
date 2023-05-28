//package com.vn.wecare.feature.home.step_count.di
//
//import android.content.Context
//import android.content.SharedPreferences
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Qualifier
//import javax.inject.Singleton
//
//const val STEP_COUNT_SHARED_PREF = "step_count_shared_pref"
//
//@Qualifier
//@Retention(AnnotationRetention.RUNTIME)
//annotation class StepCountSharePref
//
//@Module
//@InstallIn(SingletonComponent::class)
//object StepCountSharePrefModule {
//
//    @Singleton
//    @Provides
//    @StepCountSharePref
//    fun provideStepCountSharedPref(@ApplicationContext context: Context):  SharedPreferences =
//        context.getSharedPreferences(STEP_COUNT_SHARED_PREF, Context.MODE_PRIVATE)
//}