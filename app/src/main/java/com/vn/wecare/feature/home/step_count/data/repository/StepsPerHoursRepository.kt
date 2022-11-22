package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.core.data.Result
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import kotlinx.coroutines.flow.Flow

class StepsPerHoursRepository(
    private val stepsLocalDataSource: StepsDatasource<StepsPerHour>
) : StepsRepository<StepsPerHour> {

    /**
     * If insert new steps per hour to room successful than save it to remote db
     */
    override suspend fun insert(input: StepsPerHour) {
        stepsLocalDataSource.insert(input)
    }

    // This function should only be called in local db
    override suspend fun delete(input: List<StepsPerHour>) {
        stepsLocalDataSource.delete(input)
    }

    // This function should only be called in local db
    override suspend fun deleteAll() {
        stepsLocalDataSource.deleteAll()
    }

    override fun getStepsPerDayWithHours(): Flow<Result<List<StepsPerDayWithHours>>>? {
        return stepsLocalDataSource.getStepsPerDayWithHours()
    }
}