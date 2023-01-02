package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.feature.home.step_count.data.dao.StepsPerDayDao
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import com.vn.wecare.feature.home.step_count.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepsPerDayRepository @Inject constructor(
    private val stepsPerDayDao: StepsPerDayDao,
) {

    suspend fun insertStepsPerDay(stepsPerDay: StepsPerDay) {
        stepsPerDayDao.insertDay(stepsPerDay.toEntity())
    }

    suspend fun deleteStepsPerDay(stepsPerDay: StepsPerDay) : Int {
        return stepsPerDayDao.deleteDay(stepsPerDay.toEntity())
    }

    suspend fun deleteAllStepsPerDay() {
        stepsPerDayDao.deleteAllDays()
    }

    fun getStepsPerDay(dayId: String) : Flow<StepsPerDayEntity?> {
        return stepsPerDayDao.getStepsPerDay(dayId)
    }
}