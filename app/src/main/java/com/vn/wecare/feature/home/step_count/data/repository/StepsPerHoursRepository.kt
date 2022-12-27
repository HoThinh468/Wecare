package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.core.data.Result
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayWithHours
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StepsPerHoursRepository @Inject constructor(
    private val stepsLocalDataSource: StepsDatasource<StepsPerHour>,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StepsRepository<StepsPerHour> {

    /**
     * If insert new steps per hour to room successful than save it to remote db
     */
    override suspend fun insert(input: StepsPerHour) {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { stepsLocalDataSource.insert(input) }
            }
        }
    }

    // This function should only be called in local db
    override suspend fun delete(input: List<StepsPerHour>) {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { stepsLocalDataSource.delete(input) }
            }
        }
    }

    // This function should only be called in local db
    override suspend fun deleteAll() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { stepsLocalDataSource.deleteAll() }
            }
        }
    }

    override fun getStepsPerDayWithHours(dayId: String): Flow<List<StepsPerDayWithHours>>? {
        return stepsLocalDataSource.getStepsPerDayWithHours(dayId)
    }
}