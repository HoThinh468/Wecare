package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.feature.home.step_count.data.dao.StepsPerDayDao
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import com.vn.wecare.feature.home.step_count.data.model.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepsPerDayRepository @Inject constructor(
    // TODO: Add a variable to interact with remote data source
    private val stepsPerDayDao: StepsPerDayDao
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

    // TODO Create a function to load data from remote and insert to local when user login

}