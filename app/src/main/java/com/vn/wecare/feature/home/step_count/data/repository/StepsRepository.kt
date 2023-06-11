package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import kotlinx.coroutines.flow.Flow

interface StepsRepository<I> {

    suspend fun insert(input: I)

    suspend fun delete(input: List<I>)

    suspend fun deleteAll()

    fun getStepsPerHourWithDayId(dayId: String): Flow<List<StepsPerHourEntity?>>
}