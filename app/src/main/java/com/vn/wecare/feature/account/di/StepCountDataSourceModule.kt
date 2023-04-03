package com.vn.wecare.feature.account.di

import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.step_count.data.dao.StepsPerDayDao
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.datasource.local.LocalStepsPerDayDataSource
import com.vn.wecare.feature.home.step_count.data.datasource.remote.FirebaseStepsPerDayDataSource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalStepsCountPerDayDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteStepsCountPerDayDatasource

@Module
@InstallIn(SingletonComponent::class)
class StepCountDataSourceModule {

    @Singleton
    @RemoteStepsCountPerDayDatasource
    @Provides
    fun provideRemoteStepsPerDayDataSource(
        db: FirebaseFirestore, accountService: AccountService
    ): StepsDatasource<StepsPerDay> {
        return FirebaseStepsPerDayDataSource(
            db, accountService
        )
    }

    @Singleton
    @LocalStepsCountPerDayDataSource
    @Provides
    fun provideLocalStepsPerDayDataSource(
        stepsPerDayDao: StepsPerDayDao
    ): StepsDatasource<StepsPerDayEntity> {
        return LocalStepsPerDayDataSource(stepsPerDayDao)
    }
}