package com.vn.wecare.feature.authentication.ui.di

import com.vn.wecare.feature.authentication.ui.service.AccountService
import com.vn.wecare.feature.authentication.ui.service.AccountServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AccountServiceModule {

    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
}