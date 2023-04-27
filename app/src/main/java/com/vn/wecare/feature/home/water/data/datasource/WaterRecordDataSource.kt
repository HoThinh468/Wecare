package com.vn.wecare.feature.home.water.data.datasource

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface WaterRecordDataSource {

    suspend fun insert(recordEntity: WaterRecordEntity)

    suspend fun delete(recordEntity: WaterRecordEntity)

    suspend fun deleteAll()

    fun getRecordWithId(dateTime: Calendar): Flow<Response<WaterRecordEntity?>>

    fun getRecordsWithDayId(day: Int, month: Int, year: Int): Flow<Response<List<WaterRecordEntity>?>>

    suspend fun updateRecordAmountWithId(amount: Int, record: WaterRecordEntity)
}