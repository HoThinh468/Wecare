package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.feature.home.step_count.data.dao.StepsPerHourDao
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import com.vn.wecare.feature.home.step_count.data.entity.toModel
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.feature.home.step_count.data.model.toEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepsPerHoursRepository @Inject constructor(
    private val stepsPerHourDao: StepsPerHourDao
) {

    suspend fun insertStepsPerHour(stepsPerHour: StepsPerHour) {
        stepsPerHourDao.insertHour(stepsPerHour.toEntity())
    }

    fun getStepsPerDayWithHours() : List<StepsPerHour> {
        val hours: MutableList<StepsPerHour> = arrayListOf()
        for (item in stepsPerHourDao.getStepsPerDayWithHours()) {
            hours.add(item.hour.toModel())
        }
        return hours
    }

    suspend fun deleteStepsPerDayWithHour(stepsPerHours: List<StepsPerHour>) : Int {
        val hours: MutableList<StepsPerHourEntity> = arrayListOf()
        for (item in stepsPerHours) {
            hours.add(item.toEntity())
        }
        return stepsPerHourDao.deleteHours(hours)
    }

    suspend fun deleteAllHours() {
        stepsPerHourDao.deleteAllHours()
    }
}