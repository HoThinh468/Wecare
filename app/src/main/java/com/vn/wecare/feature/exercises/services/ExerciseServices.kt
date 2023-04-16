package com.vn.wecare.feature.exercises.services

import com.vn.wecare.core.data.Response
import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.core.model.ListDone
import com.vn.wecare.core.model.ListReviewsItem
import kotlinx.coroutines.flow.Flow

interface ExerciseServices {

    suspend fun getReviewsList(type: ExerciseType, index: Int) : Flow<Response<List<ListReviewsItem>>>
    suspend fun likeReview(type: ExerciseType, index: Int, indexReview: Int, newLikeCount: Int): Response<Boolean>
    suspend fun getReviewLikeCount(type: ExerciseType, index: Int, indexReview: Int): Flow<Response<Int>>
    suspend fun updateListLikeAccount(type: ExerciseType, index: Int, indexReview: Int): Response<Boolean>
    suspend fun getListDoneFullBody(): Flow<Response<ListDone?>?>
    suspend fun addNewReview(type: ExerciseType, index: Int, content: String, rate: Int): Response<Boolean>
    suspend fun updateListDoneFullBody(dateIndex: Int): Response<Boolean>
}