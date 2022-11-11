package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.feature.home.step_count.data.dao.StepsPerHourDao
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import kotlinx.coroutines.flow.Flow

class StepsPerHourLocalRepository(
    private val stepsPerHourDao: StepsPerHourDao
) : StepsPerHourRepository {

    override fun getStepsPerDayWithHours(
        userId: String,
        dayId: String
    ): Flow<List<StepsPerDayWithHours>> = stepsPerHourDao.getStepsPerDayWithHours(userId, dayId)

    override suspend fun insertNewHour(stepsPerHourEntity: StepsPerHourEntity) {
        stepsPerHourDao.insertNewHour(stepsPerHourEntity)
    }

    override suspend fun deleteAllHours() {
        stepsPerHourDao.deleteAllHours()
    }

    override suspend fun deleteFurthest24Hours(stepsPerHourEntity: StepsPerHourEntity) {
        stepsPerHourDao.deleteFurthest24Hours(stepsPerHourEntity)
    }
}