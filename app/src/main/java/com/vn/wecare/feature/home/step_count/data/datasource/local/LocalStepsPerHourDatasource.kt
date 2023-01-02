package com.vn.wecare.feature.home.step_count.data.datasource.local

import com.vn.wecare.feature.home.step_count.data.dao.StepsPerHourDao
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.feature.home.step_count.data.model.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalStepPerHourDatasource @Inject constructor(
    private val stepsPerHourDao: StepsPerHourDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StepsDatasource<StepsPerHour> {

    override suspend fun insert(input: StepsPerHour) = withContext(ioDispatcher) {
        stepsPerHourDao.insertHour(input.toEntity())
    }

    override suspend fun delete(inputs: List<StepsPerHour>) = withContext(ioDispatcher) {
        val hours: MutableList<StepsPerHourEntity> = arrayListOf()
        for (item in inputs) {
            hours.add(item.toEntity())
        }
        stepsPerHourDao.deleteHours(hours)
    }

    override fun getStepsPerDayWithHours(dayId: String): Flow<List<StepsPerDayWithHours>> {
        return stepsPerHourDao.getStepsPerDayWithHours(dayId).map {
            it
        }
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        stepsPerHourDao.deleteAllHours()
    }

    override fun getStepsPerDay(dayId: String): Flow<StepsPerDay>? {
        TODO("Not yet implemented")
    }
}