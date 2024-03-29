package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.datasource.remote.FirebaseStepsPerHourDataSource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerHourEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class StepsPerHoursRepository @Inject constructor(
    private val stepsLocalDataSource: StepsDatasource<StepsPerHour>,
    private val firebaseStepsPerHourDataSource: FirebaseStepsPerHourDataSource
) : StepsRepository<StepsPerHour> {

    /**
     * If insert new steps per hour to room successful than save it to remote db
     */
    override suspend fun insert(input: StepsPerHour) {
        coroutineScope {
            launch { stepsLocalDataSource.insert(input) }
        }
    }

    // This function should only be called in local db
    override suspend fun delete(input: List<StepsPerHour>) {
        coroutineScope {
            launch { stepsLocalDataSource.delete(input) }
        }
    }

    // This function should only be called in local db
    override suspend fun deleteAll() {
        coroutineScope {
            launch { stepsLocalDataSource.deleteAll() }
        }
    }

    override fun getStepsPerHourWithDayId(dayId: String): Flow<List<StepsPerHourEntity?>> {
        return stepsLocalDataSource.getStepsPerHourWithDayId(dayId)
    }

    suspend fun insertStepsPerHourToFirebase(input: StepsPerHour) {
        firebaseStepsPerHourDataSource.insert(input)
    }

    fun getStepsPerHoursFromRemoteWithDayId(dayId: String): Flow<List<StepsPerHour?>> {
        return firebaseStepsPerHourDataSource.getStepsPerHours(dayId)
    }

    fun getStepsPerHourWithHourId(hourId: String): Flow<StepsPerHourEntity?> {
        return stepsLocalDataSource.getStepsPerHourWithHourId(hourId)
    }
}