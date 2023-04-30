package com.vn.wecare.feature.exercises.usecase

import com.vn.wecare.core.model.ExerciseType
import com.vn.wecare.feature.exercises.services.ExerciseServices

class AddNewExerciseHistory(
    private val services: ExerciseServices
) {
    suspend operator fun invoke(type: ExerciseType, kcal: Float, duration: Int) =
        services.addNewExerciseHistory(type, kcal, duration)
}