package com.vn.wecare.feature.home.water.tracker.data.datasource

import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.home.water.tracker.data.model.WaterRecordEntity
import com.vn.wecare.feature.home.water.tracker.data.dao.WaterRecordDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WaterRecordLocalDataSource @Inject constructor(
    private val waterRecordDao: WaterRecordDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WaterRecordDataSource {

    override suspend fun insert(recordEntity: WaterRecordEntity) = withContext(ioDispatcher) {
        waterRecordDao.insertRecord(recordEntity)
    }

    override suspend fun delete(recordEntity: WaterRecordEntity) = withContext(ioDispatcher) {
        waterRecordDao.deleteRecord(recordEntity)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        waterRecordDao.deleteAllRecords()
    }

    override fun getRecordWithId(recordId: Long): Flow<Response<WaterRecordEntity?>> {
        TODO("Not yet implemented")
    }

    override fun getRecordsWithDayId(dayId: String): Flow<Response<List<WaterRecordEntity>?>> =
        flow {
            try {
                val result = waterRecordDao.getRecordWithDayId(dayId)
                if (result != null) {
                    emit(Response.Success(result))
                } else emit(Response.Error(null))
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }.flowOn(ioDispatcher)
}