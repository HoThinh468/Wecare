package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.feature.home.step_count.data.dao.StepsPerDayDao
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import kotlinx.coroutines.flow.Flow

interface StepsPerDayRepository {
    fun getDayById(dayId: String) : Flow<StepsPerDayEntity>

    suspend fun insertNewDay(stepsPerDayEntity: StepsPerDayEntity)

    suspend fun deleteAllDays()

    suspend fun deleteFurthestDay(stepsPerDayEntity: StepsPerDayEntity)
}