package com.vn.wecare.feature.home.step_count.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.WecareDatabase
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.step_count.data.datasource.local.LocalStepPerHourDatasource
import com.vn.wecare.feature.home.step_count.data.datasource.remote.FirebaseStepsPerHourDataSource
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerHoursRepository
import com.vn.wecare.feature.home.step_count.usecase.DashboardUseCase
import com.vn.wecare.feature.home.step_count.usecase.GetStepsPerDayUsecase
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

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FirebaseStepsPerHourDataSourceAnnotation

@Module
@InstallIn(SingletonComponent::class)
class StepsDataSourceModule {

    @Singleton
    @LocalStepsPerHourDatasource
    @Provides
    fun provideLocalStepsPerHourDataSource(
        database: WecareDatabase, @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): LocalStepPerHourDatasource {
        return LocalStepPerHourDatasource(
            database.stepsPerHourDao(), ioDispatcher
        )
    }

    @Singleton
    @FirebaseStepsPerHourDataSourceAnnotation
    @Provides
    fun provideFirebaseStepsPerHourDataSource(
        db: FirebaseFirestore, accountService: AccountService
    ): FirebaseStepsPerHourDataSource {
        return FirebaseStepsPerHourDataSource(
            db, accountService
        )
    }

    @Singleton
    @Provides
    fun provideGetStepsPerDayUsecase(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): GetStepsPerDayUsecase {
        return GetStepsPerDayUsecase(
            firebaseFirestore, firebaseAuth
        )
    }

    @Singleton
    @Provides
    fun provideDashboardUseCase(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): DashboardUseCase {
        return DashboardUseCase(
            firebaseFirestore, firebaseAuth
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
object StepsPerHourRepositoryModule {

    @Singleton
    @Provides
    fun provideStepsPerHourRepository(
        @LocalStepsPerHourDatasource stepPerHourDatasource: LocalStepPerHourDatasource,
        @FirebaseStepsPerHourDataSourceAnnotation firebaseStepsPerHourDataSource: FirebaseStepsPerHourDataSource
    ): StepsPerHoursRepository {
        return StepsPerHoursRepository(
            stepPerHourDatasource, firebaseStepsPerHourDataSource
        )
    }

}