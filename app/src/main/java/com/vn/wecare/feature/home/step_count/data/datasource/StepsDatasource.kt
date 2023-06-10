package com.vn.wecare.feature.home.step_count.data.datasource

import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import kotlinx.coroutines.flow.Flow

interface StepsDatasource<I> {

    suspend fun insert(input: I)

    suspend fun delete(inputs: List<I>)

    suspend fun deleteAll()

    fun getStepsPerHourWithDayId(dayId: String): Flow<List<StepsPerHourEntity?>>

    fun getStepsPerDay(dayId: String): Flow<I?>

    fun getStepsPerHourWithHourId(hourId: String): Flow<StepsPerHourEntity?>
}