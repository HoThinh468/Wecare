package com.vn.wecare.feature.home.water.tracker.data.datasource

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.water.tracker.data.model.WaterRecordEntity
import kotlinx.coroutines.flow.Flow

interface WaterRecordDataSource {

    suspend fun insert(recordEntity: WaterRecordEntity)

    suspend fun delete(recordEntity: WaterRecordEntity)

    suspend fun deleteAll()

    fun getRecordWithId(recordId: Long): Flow<Response<WaterRecordEntity?>>

    fun getRecordsWithDayId(dayId: String): Flow<Response<List<WaterRecordEntity>?>>
}