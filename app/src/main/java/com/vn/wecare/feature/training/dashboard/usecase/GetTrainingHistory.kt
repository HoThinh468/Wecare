package com.vn.wecare.feature.training.dashboard.usecase

import com.vn.wecare.feature.training.TrainingHistoryRepo

class GetTrainingHistory(
    private val repo: TrainingHistoryRepo
) {
    operator fun invoke() = repo.getTrainingHistoryFromFireStore()
}