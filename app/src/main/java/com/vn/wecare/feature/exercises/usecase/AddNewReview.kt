package com.vn.wecare.feature.exercises.usecase

import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.feature.exercises.services.ExerciseServices

class AddNewReview (
    private val services: ExerciseServices
) {
    suspend operator fun invoke(
        type: ExerciseType, index: Int, content: String, rate: Int
    ) = services.addNewReview(type, index, content, rate)
}
