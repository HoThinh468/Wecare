package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.core.data.Result
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import kotlinx.coroutines.flow.Flow

interface StepsRepository<I> {

    suspend fun insert(input: I)

    suspend fun delete(input: List<I>)

    suspend fun deleteAll()

    fun getStepsPerDayWithHours(dayId: String): Flow<List<StepsPerDayWithHours?>>
}