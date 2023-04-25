package com.vn.wecare.feature.home.water.data

import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.home.water.data.datasource.WaterRecordDataSource
import com.vn.wecare.feature.home.water.data.di.LocalWaterRecordDataSource
import com.vn.wecare.feature.home.water.data.di.RemoteWaterRecordDataSource
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class WaterRecordRepository @Inject constructor(
    @LocalWaterRecordDataSource private val localDataSource: WaterRecordDataSource,
    @RemoteWaterRecordDataSource private val remoteDataSource: WaterRecordDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun insertRecord(record: WaterRecordEntity) {
        localDataSource.insert(record)
        remoteDataSource.insert(record)
    }

    fun getRecordsWithDayId(
        day: Int, month: Int, year: Int
    ): Flow<Response<List<WaterRecordEntity>?>> {
        return localDataSource.getRecordsWithDayId(day, month, year)
    }

    fun getRecordWithDayIdFromRemote(
        day: Int, month: Int, year: Int
    ): Flow<Response<List<WaterRecordEntity>?>> {
        return remoteDataSource.getRecordsWithDayId(day, month, year)
    }

    suspend fun deleteRecord(record: WaterRecordEntity) {
        localDataSource.delete(record)
        remoteDataSource.delete(record)
    }

    suspend fun deleteAllRecords() {
        localDataSource.deleteAll()
    }

    suspend fun updateRecordAmountWithId(amount: Int, record: WaterRecordEntity) {
        localDataSource.updateRecordAmountWithId(amount, record)
        remoteDataSource.updateRecordAmountWithId(amount, record)
    }

    suspend fun refreshDataOfCurrentDay() {
        val currentTime = Calendar.getInstance()
        withContext(ioDispatcher) {
            remoteDataSource.getRecordsWithDayId(
                currentTime.get(Calendar.DAY_OF_MONTH),
                currentTime.get(Calendar.MONTH),
                currentTime.get(Calendar.YEAR)
            ).collect {
                if (it is Response.Success && it.data != null) {
                    localDataSource.deleteAll()
                    repeat(it.data.size) { i ->
                        localDataSource.insert(it.data[i])
                    }
                }
            }
        }
    }
}