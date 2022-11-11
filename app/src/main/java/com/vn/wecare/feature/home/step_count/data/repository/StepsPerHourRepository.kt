package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import kotlinx.coroutines.flow.Flow

interface StepsPerHourRepository {
    fun getStepsPerDayWithHours(userId: String, dayId: String) : Flow<List<StepsPerDayWithHours>>

    suspend fun insertNewHour(stepsPerHourEntity: StepsPerHourEntity)

    suspend fun deleteAllHours()

    suspend fun deleteFurthest24Hours(stepsPerHourEntity: StepsPerHourEntity)
}