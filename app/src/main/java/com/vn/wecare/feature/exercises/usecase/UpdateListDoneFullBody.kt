package com.vn.wecare.feature.exercises.usecase

import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.feature.exercises.services.ExerciseServices

class UpdateListDoneFullBody(
    private val services: ExerciseServices
) {
    suspend operator fun invoke(
        index: Int
    ) = services.updateListDoneFullBody(index)
}