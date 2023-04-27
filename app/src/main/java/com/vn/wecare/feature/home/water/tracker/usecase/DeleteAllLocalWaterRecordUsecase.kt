package com.vn.wecare.feature.home.water.tracker.usecase

import com.vn.wecare.feature.home.water.data.WaterRecordRepository
import javax.inject.Inject

class DeleteAllLocalWaterRecordUsecase @Inject constructor(
    private val waterRecordRepository: WaterRecordRepository
) {
    suspend fun deleteAllRecords() {
        waterRecordRepository.deleteAllRecords()
    }
}