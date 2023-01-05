package com.vn.wecare.feature.home.step_count.data.datasource

import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import kotlinx.coroutines.flow.Flow

interface StepsDatasource<I> {

    suspend fun insert(input: I)

    suspend fun delete(inputs: List<I>)

    suspend fun deleteAll()

    fun getStepsPerDayWithHours(dayId: String): Flow<List<StepsPerDayWithHours?>>

    fun getStepsPerDay(dayId: String): Flow<I?>
}