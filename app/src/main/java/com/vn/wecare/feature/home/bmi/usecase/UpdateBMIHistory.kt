package com.vn.wecare.feature.home.bmi.usecase

import com.vn.wecare.feature.home.bmi.model.BMIHistoryEntity
import com.vn.wecare.feature.home.bmi.service.BMIServices

class UpdateBMIHistory(
    private val services: BMIServices
) {
    suspend operator fun invoke(updatedList: List<BMIHistoryEntity>) =
        services.updateListHistory(updatedList)
}
