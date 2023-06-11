package com.vn.wecare.feature.home.water.tracker.usecase

import com.vn.wecare.feature.home.water.data.WaterRecordRepository
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity
import javax.inject.Inject

class WaterRecordUsecase @Inject constructor(
    private val waterRecordRepository: WaterRecordRepository
) {
    suspend fun insertNewRecord(record: WaterRecordEntity) {
        waterRecordRepository.insertRecord(record)
    }

    suspend fun deleteRecord(record: WaterRecordEntity) {
        waterRecordRepository.deleteRecord(record)
    }
}