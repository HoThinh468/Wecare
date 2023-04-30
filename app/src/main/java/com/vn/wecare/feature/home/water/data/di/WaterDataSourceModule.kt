package com.vn.wecare.feature.home.water.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.WecareDatabase
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.water.data.WaterRecordRepository
import com.vn.wecare.feature.home.water.data.datasource.WaterRecordDataSource
import com.vn.wecare.feature.home.water.data.datasource.WaterRecordLocalDataSource
import com.vn.wecare.feature.home.water.data.datasource.WaterRecordRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalWaterRecordDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteWaterRecordDataSource

@Module
@InstallIn(SingletonComponent::class)
class WaterDataSourceModule {

    @Singleton
    @LocalWaterRecordDataSource
    @Provides
    fun provideLocalWaterRecordDataSource(
        database: WecareDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): WaterRecordDataSource {
        return WaterRecordLocalDataSource(
            database.waterRecordDao(), ioDispatcher
        )
    }

    @Singleton
    @RemoteWaterRecordDataSource
    @Provides
    fun provideRemoteWaterRecordDataSource(
        firestore: FirebaseFirestore,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        accountService: AccountService
    ): WaterRecordDataSource {
        return WaterRecordRemoteDataSource(
            firestore, ioDispatcher, accountService
        )
    }
}