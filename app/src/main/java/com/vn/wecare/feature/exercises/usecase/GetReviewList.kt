package com.vn.wecare.feature.exercises.usecase

import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.feature.exercises.services.ExerciseServices

class GetReviewList(
    private val services: ExerciseServices
) {
    suspend operator fun invoke(
        type: ExerciseType,
        index: Int
    ) = services.getReviewsList(type, index)
}