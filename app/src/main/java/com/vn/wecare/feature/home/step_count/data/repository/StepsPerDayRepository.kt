package com.vn.wecare.feature.home.step_count.data.repository

import com.vn.wecare.feature.account.di.LocalStepsCountPerDayDataSource
import com.vn.wecare.feature.account.di.RemoteStepsCountPerDayDatasource
import com.vn.wecare.feature.home.step_count.data.datasource.StepsDatasource
import com.vn.wecare.feature.home.step_count.data.entity.StepsPerDayEntity
import com.vn.wecare.feature.home.step_count.data.model.StepsPerDay
import com.vn.wecare.feature.home.step_count.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepsPerDayRepository @Inject constructor(
    @LocalStepsCountPerDayDataSource private val localStepsPerDayDataSource: StepsDatasource<StepsPerDayEntity>,
    @RemoteStepsCountPerDayDatasource private val firebaseStepsPerDayDataSource: StepsDatasource<StepsPerDay>
) {

    suspend fun insertStepsPerDay(stepsPerDay: StepsPerDay) {
        localStepsPerDayDataSource.insert(stepsPerDay.toEntity())
    }

    suspend fun deleteStepsPerDay(stepsPerDay: List<StepsPerDayEntity>) {
        return localStepsPerDayDataSource.delete(stepsPerDay)
    }

    suspend fun deleteAllStepsPerDay() {
        localStepsPerDayDataSource.deleteAll()
    }

    fun getStepsPerDay(dayId: String): Flow<StepsPerDayEntity?> {
        return localStepsPerDayDataSource.getStepsPerDay(dayId)
    }

    suspend fun insertStepsPerDayToFirestore(stepsPerDay: StepsPerDay) {
        firebaseStepsPerDayDataSource.insert(stepsPerDay)
    }

    fun getStepsPerDayWithIdFromFirestore(dayId: String): Flow<StepsPerDay?> {
        return firebaseStepsPerDayDataSource.getStepsPerDay(dayId)
    }
}