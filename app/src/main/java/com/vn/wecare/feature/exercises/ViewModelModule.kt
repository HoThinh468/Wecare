package com.vn.wecare.feature.exercises

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.feature.exercises.services.ExerciseServices
import com.vn.wecare.feature.exercises.services.ExerciseServicesImpl
import com.vn.wecare.feature.exercises.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideExerciseServices(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): ExerciseServices = ExerciseServicesImpl(auth, firestore)

    @Provides
    fun provideExerciseUC(
        services: ExerciseServices
    ) = Usecases(
        getReviewList = GetReviewList(services),
        likeReview = LikeReview(services),
        getReviewLikeCount = GetReviewLikeCount(services),
        updateListLikeAccount = UpdateListLikeAccount(services),
        getListDoneFullBody = GetListDoneFullBody(services),
        addNewReview = AddNewReview(services),
        updateListDoneFullBody = UpdateListDoneFullBody(services)
    )
}