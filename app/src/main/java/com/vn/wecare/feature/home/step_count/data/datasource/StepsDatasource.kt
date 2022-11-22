package com.vn.wecare.feature.home.step_count.data.datasource

import com.vn.wecare.core.data.Result
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import kotlinx.coroutines.flow.Flow

interface StepsDatasource<I> {

    suspend fun insert(input: I)

    suspend fun delete(inputs: List<I>)

    suspend fun deleteAll()

    fun getStepsPerDayWithHours(): Flow<Result<List<StepsPerDayWithHours>>>?
}