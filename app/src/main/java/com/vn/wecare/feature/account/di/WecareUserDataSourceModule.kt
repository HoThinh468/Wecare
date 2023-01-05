package com.vn.wecare.feature.account.di

import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.WecareDatabase
import com.vn.wecare.feature.account.data.datasource.FirebaseWecareUserDataSource
import com.vn.wecare.feature.account.data.datasource.LocalWecareUserDataSource
import com.vn.wecare.feature.account.data.datasource.WecareUserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalUserDatasource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FirebaseUserDatasource

@Module
@InstallIn(SingletonComponent::class)
class WecareUserDataSourceModule {

    @Singleton
    @LocalUserDatasource
    @Provides
    fun provideLocalUserDataSource(
        database: WecareDatabase
    ): WecareUserDataSource {
        return LocalWecareUserDataSource(
            database.userDao()
        )
    }

    @Singleton
    @FirebaseUserDatasource
    @Provides
    fun provideFirebaseUserDataSource(
        firestore: FirebaseFirestore
    ): WecareUserDataSource {
        return FirebaseWecareUserDataSource(firestore)
    }
}