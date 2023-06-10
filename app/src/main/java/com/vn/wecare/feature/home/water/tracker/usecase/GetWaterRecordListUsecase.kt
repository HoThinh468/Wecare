package com.vn.wecare.feature.home.water.tracker.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.water.data.WaterRecordRepository
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class GetWaterRecordListUsecase @Inject constructor(
    private val repository: WaterRecordRepository
) {
    fun getCurrentDay(): Flow<Response<List<WaterRecordEntity>?>> {
        val currentTime = Calendar.getInstance()
        return repository.getRecordsWithDayId(
            currentTime.get(Calendar.DAY_OF_MONTH),
            currentTime.get(Calendar.MONTH),
            currentTime.get(Calendar.YEAR)
        )
    }
}