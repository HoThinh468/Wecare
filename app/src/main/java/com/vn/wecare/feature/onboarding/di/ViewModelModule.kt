package com.vn.wecare.feature.onboarding.di

import com.vn.wecare.feature.account.data.UserRepository
import com.vn.wecare.feature.account.data.datasource.WecareUserDataSource
import com.vn.wecare.feature.account.di.FirebaseUserDatasource
import com.vn.wecare.feature.account.di.LocalUserDatasource
import com.vn.wecare.feature.account.usecase.UpdateWecareUserUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideUsecase(
        userRepository: UserRepository
    ): UpdateWecareUserUsecase {
        return UpdateWecareUserUsecase(userRepository)
    }

    @Provides
    fun provideUserRepository(
        @LocalUserDatasource localWecareUserDataSource: WecareUserDataSource,
        @FirebaseUserDatasource firebaseWecareUserDataSource: WecareUserDataSource
    ): UserRepository {
        return UserRepository(
            localWecareUserDataSource, firebaseWecareUserDataSource
        )
    }
}