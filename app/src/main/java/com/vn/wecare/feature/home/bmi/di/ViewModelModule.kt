package com.vn.wecare.feature.home.bmi.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.feature.home.bmi.service.BMIServices
import com.vn.wecare.feature.home.bmi.service.BMIServicesImpl
import com.vn.wecare.feature.home.bmi.usecase.AddBMIHistory
import com.vn.wecare.feature.home.bmi.usecase.BMIUseCase
import com.vn.wecare.feature.home.bmi.usecase.FetchListHistory
import com.vn.wecare.feature.home.bmi.usecase.UpdateBMIHistory
import com.vn.wecare.feature.training.dashboard.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideBMIService(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): BMIServices = BMIServicesImpl(auth, firestore)

    @Provides
    fun provideBMIUseCase(
        services: BMIServices
    )= BMIUseCase(
        addBMIHistory = AddBMIHistory(services),
        fetchListHistory = FetchListHistory(services),
        updateBMIHistory = UpdateBMIHistory(services)
    )
}
