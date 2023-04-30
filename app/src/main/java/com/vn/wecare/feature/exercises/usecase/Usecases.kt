package com.vn.wecare.feature.exercises.usecase

data class Usecases(
    val getReviewList: GetReviewList,
    val likeReview: LikeReview,
    val getReviewLikeCount:GetReviewLikeCount,
    val updateListLikeAccount: UpdateListLikeAccount,
    val getListDoneFullBody: GetListDoneFullBody,
    val addNewReview: AddNewReview,
    val updateListDoneFullBody: UpdateListDoneFullBody,
    val getListExerciseHistory: GetListExerciseHistory,
    val addNewExerciseHistory: AddNewExerciseHistory,
)