package com.vn.wecare.feature.home.step_count.data.datasource.local

import com.vn.wecare.feature.home.step_count.data.dao.StepsPerDayDao
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import com.vn.wecare.feature.home.step_count.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalStepsPerDayDataSource @Inject constructor(
    private val stepsPerDayDao: StepsPerDayDao
) : StepsDatasource<StepsPerDayEntity> {

    override suspend fun deleteAll() {
        stepsPerDayDao.deleteAllDays()
    }

    override fun getStepsPerHourWithDayId(dayId: String): Flow<List<StepsPerHourEntity?>> {
        TODO("Not yet implemented")
    }

    override fun getStepsPerDay(dayId: String): Flow<StepsPerDayEntity?> {
        return stepsPerDayDao.getStepsPerDay(dayId)
    }

    override fun getStepsPerHourWithHourId(hourId: String): Flow<StepsPerHourEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(input: StepsPerDayEntity) {
        stepsPerDayDao.insertDay(input)
    }

    override suspend fun delete(inputs: List<StepsPerDayEntity>) {
        for (item in inputs) {
            stepsPerDayDao.deleteDay(item)
        }
    }
}