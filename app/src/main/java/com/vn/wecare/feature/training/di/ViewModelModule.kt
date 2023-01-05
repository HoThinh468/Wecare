package com.vn.wecare.feature.training.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vn.wecare.feature.training.TrainingHistoryRepo
import com.vn.wecare.feature.training.dashboard.history.TrainingHistoryRepoImpl
import com.vn.wecare.feature.training.dashboard.usecase.AddTrainingHistory
import com.vn.wecare.feature.training.dashboard.usecase.GetTrainingHistory
import com.vn.wecare.feature.training.dashboard.usecase.GetWeeklyCheck
import com.vn.wecare.feature.training.dashboard.usecase.UseCases
import com.vn.wecare.feature.training.utils.formatCurrentMonth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.time.LocalDateTime

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun trainingHistoryRef() = Firebase.firestore
        .collection("training_history")
        .document(Firebase.auth.currentUser!!.uid)
        .collection("list")

    @Provides
    fun provideHistoryRepo(
        trainingHistoryRef: CollectionReference
    ): TrainingHistoryRepo = TrainingHistoryRepoImpl(trainingHistoryRef)

    @Provides
    fun provideGetTrainingHistoryUC(
        repo: TrainingHistoryRepo
    )= UseCases(
        getTrainingHistory = GetTrainingHistory(repo),
        addTrainingHistory = AddTrainingHistory(repo),
        getWeeklyCheck = GetWeeklyCheck(repo)
    )
}
