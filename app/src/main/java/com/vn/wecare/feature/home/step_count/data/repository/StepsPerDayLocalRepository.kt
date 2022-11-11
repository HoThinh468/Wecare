package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.feature.home.step_count.data.dao.StepsPerDayDao
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import kotlinx.coroutines.flow.Flow

class StepsPerDayLocalRepository(
    private val stepsPerDayDao: StepsPerDayDao
) : StepsPerDayRepository {
    override fun getDayById(dayId: String): Flow<StepsPerDayEntity> =
        stepsPerDayDao.getDayById(dayId)

    
    override suspend fun insertNewDay(stepsPerDayEntity: StepsPerDayEntity) {
        stepsPerDayDao.insertNewDay(stepsPerDayEntity)
    }

    override suspend fun deleteAllDays() {
        stepsPerDayDao.deleteAllDays()
    }

    override suspend fun deleteFurthestDay(stepsPerDayEntity: StepsPerDayEntity) {
        stepsPerDayDao.deleteFurthestDay(stepsPerDayEntity)
    }
}