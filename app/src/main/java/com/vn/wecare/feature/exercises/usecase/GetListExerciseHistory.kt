package com.vn.wecare.feature.exercises.usecase

import com.vn.wecare.feature.exercises.services.ExerciseServices

class GetListExerciseHistory(
    private val services: ExerciseServices
) {
    suspend operator fun invoke() = services.getListHistory()
}
