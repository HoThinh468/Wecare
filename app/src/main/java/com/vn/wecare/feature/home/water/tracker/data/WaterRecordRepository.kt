package com.vn.wecare.feature.home.water.tracker.data

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.water.tracker.data.datasource.WaterRecordDataSource
import com.vn.wecare.feature.home.water.tracker.data.di.LocalWaterRecordDataSource
import com.vn.wecare.feature.home.water.tracker.data.di.RemoteWaterRecordDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class WaterRecordRepository @Inject constructor(
    @LocalWaterRecordDataSource private val localDataSource: WaterRecordDataSource,
    @RemoteWaterRecordDataSource private val remoteDataSource: WaterRecordDataSource
) {
    suspend fun insertRecord(record: WaterRecordEntity) {
        localDataSource.insert(record)
    }

    fun getRecordsWithDayId(dayId: String): Flow<Response<List<WaterRecordEntity>?>> {
        return localDataSource.getRecordsWithDayId(dayId)
    }
}