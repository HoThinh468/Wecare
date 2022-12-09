package com.vn.wecare.feature.home.step_count.di

import com.vn.wecare.core.data.WecareDatabase
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.datasource.local.LocalStepPerHourDatasource
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalStepsPerHourDatasource

@Module
@InstallIn(SingletonComponent::class)
class StepsDataSourceModule {

    @Singleton
    @LocalStepsPerHourDatasource
    @Provides
    fun provideLocalStepsPerHourDataSource(
        database: WecareDatabase, @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): StepsDatasource<StepsPerHour> {
        return LocalStepPerHourDatasource(
            database.stepsPerHourDao(), ioDispatcher
        )
    }
}

//@Module
//@InstallIn(SingletonComponent::class)
//object StepsPerHourRepositoryModule {
//
//    @Singleton
//    @Provides
//    fun provideStepsPerHourRepository(
//        @LocalStepsPerHourDatasource stepPerHourDatasource: LocalStepPerHourDatasource,
//        @IoDispatcher ioDispatcher: CoroutineDispatcher
//    ): StepsPerHoursRepository {
//        return StepsPerHoursRepository(
//            stepPerHourDatasource, ioDispatcher
//        )
//    }
//
//}