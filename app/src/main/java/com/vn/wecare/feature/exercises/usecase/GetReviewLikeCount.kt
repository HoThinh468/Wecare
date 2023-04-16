package com.vn.wecare.feature.exercises.usecase

import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.feature.exercises.services.ExerciseServices

class GetReviewLikeCount(
    private val services: ExerciseServices
) {
    suspend operator fun invoke(
        type: ExerciseType,
        index: Int,
        indexReview: Int
    ) = services.getReviewLikeCount(type, index, indexReview)
}
