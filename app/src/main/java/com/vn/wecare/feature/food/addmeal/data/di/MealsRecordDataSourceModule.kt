package com.vn.wecare.feature.food.addmeal.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.food.addmeal.data.datasource.MealRecordDataSource
import com.vn.wecare.feature.food.addmeal.data.datasource.MealRecordRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteMealsRecordDataSource

@Module
@InstallIn(SingletonComponent::class)
class MealsRecordDataSourceModule {

    @Singleton
    @RemoteMealsRecordDataSource
    @Provides
    fun provideRemoteMealsRecordDataSource(
        firestore: FirebaseFirestore,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        accountService: AccountService
    ): MealRecordDataSource {
        return MealRecordRemoteDataSource(firestore, ioDispatcher, accountService)
    }
}