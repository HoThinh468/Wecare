package com.vn.wecare.feature.home.water.data.datasource

import android.util.Log
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity
import com.vn.wecare.feature.home.water.data.dao.WaterRecordDao
import com.vn.wecare.feature.home.water.tracker.ui.WaterFragment
import com.vn.wecare.utils.getDayId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.Calendar
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

    override fun getRecordWithId(dateTime: Calendar): Flow<Response<WaterRecordEntity?>> {
        TODO("Not yet implemented")
    }

    override fun getRecordsWithDayId(
        day: Int, month: Int, year: Int
    ): Flow<Response<List<WaterRecordEntity>?>> = flow {
        val dayId = getDayId(day, month, year)
        try {
            val result = waterRecordDao.getRecordWithDayId(dayId)
            if (result != null) {
                emit(Response.Success(result))
            } else emit(Response.Error(null))
        } catch (e: Exception) {
            emit(Response.Error(e))
        }
    }.flowOn(ioDispatcher)

    override suspend fun updateRecordAmountWithId(amount: Int, record: WaterRecordEntity) {
        waterRecordDao.updateRecord(amount, record.recordId)
    }

    suspend fun deleteAllRecords() {
        waterRecordDao.deleteAllRecords()
    }
}