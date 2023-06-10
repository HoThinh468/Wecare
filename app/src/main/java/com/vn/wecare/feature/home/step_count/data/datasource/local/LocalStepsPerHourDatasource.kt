package com.vn.wecare.feature.home.step_count.data.datasource.local

import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.home.step_count.data.dao.StepsPerHourDao
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.feature.home.step_count.data.model.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalStepPerHourDatasource @Inject constructor(
    private val stepsPerHourDao: StepsPerHourDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
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

    override fun getStepsPerHourWithDayId(dayId: String): Flow<List<StepsPerHourEntity?>> {
        return stepsPerHourDao.getStepsPerHourWithDayId(dayId)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        stepsPerHourDao.deleteAllHours()
    }

    override fun getStepsPerDay(dayId: String): Flow<StepsPerHour?> {
        TODO("Not yet implemented")
    }

    override fun getStepsPerHourWithHourId(hourId: String): Flow<StepsPerHourEntity?> {
        return stepsPerHourDao.getStepPerHourWithHourId(hourId)
    }
}