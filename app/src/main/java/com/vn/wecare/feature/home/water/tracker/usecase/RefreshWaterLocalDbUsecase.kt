package com.vn.wecare.feature.home.water.tracker.usecase

import com.vn.wecare.feature.home.water.data.WaterRecordRepository
import javax.inject.Inject

class RefreshWaterLocalDbUsecase @Inject constructor(
    private val repository: WaterRecordRepository
) {
    suspend fun refreshWaterLocalDbWhenUserLogin() {
        repository.refreshDataOfCurrentDay()
    }
}