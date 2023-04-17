package com.vn.wecare.feature.home.water.tracker.data

import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WaterRecordRepository @Inject constructor(
    private val waterRecordDao: WaterRecordDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun insertNewRecord(recordEntity: WaterRecordEntity) = withContext(ioDispatcher) {
        waterRecordDao.insertRecord(recordEntity)
    }

    suspend fun deleteRecord(recordEntity: WaterRecordEntity) = withContext(ioDispatcher) {
        waterRecordDao.deleteRecord(recordEntity)
    }

    suspend fun deleteAllRecord() = withContext(ioDispatcher) {
        waterRecordDao.deleteAllRecords()
    }

    fun getAllRecords(): Flow<Response<List<WaterRecordEntity>?>> = flow {
        try {
            val result = waterRecordDao.getAllRecords()
            if (result != null) {
                emit(Response.Success(waterRecordDao.getAllRecords()))
            } else emit(Response.Success(null))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }.flowOn(ioDispatcher)
}