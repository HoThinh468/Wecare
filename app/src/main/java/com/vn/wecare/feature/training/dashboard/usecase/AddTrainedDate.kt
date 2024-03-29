package com.vn.wecare.feature.training.dashboard.usecase

import com.vn.wecare.feature.training.TrainingHistoryRepo
import com.vn.wecare.feature.training.dashboard.history.model.TrainingHistory

class AddTrainedDate (
    private val repo: TrainingHistoryRepo
) {
    suspend operator fun invoke(
    ) = repo.addTrainedDate()
}