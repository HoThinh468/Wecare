package com.vn.wecare.feature.account.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.authentication.service.AccountServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AccountServiceModule {

    @Singleton
    @Provides
    fun provideAccountService(
        auth: FirebaseAuth
    ): AccountService {
        return AccountServiceImpl(auth)
    }
}