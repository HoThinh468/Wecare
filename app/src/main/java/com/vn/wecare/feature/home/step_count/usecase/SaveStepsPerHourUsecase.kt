package com.vn.wecare.feature.home.step_count.usecase

import android.util.Log
import com.vn.wecare.feature.home.step_count.data.model.StepsPerHour
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerHoursRepository
import com.vn.wecare.feature.home.step_count.getCaloriesBurnedFromStepCount
import com.vn.wecare.feature.home.step_count.getMoveTimeFromStepCount
import com.vn.wecare.feature.home.step_count.ui.view.StepCountFragment
import com.vn.wecare.utils.getCurrentDayId
import com.vn.wecare.utils.getCurrentHourId
import javax.inject.Inject

class SaveStepsPerHourUsecase @Inject constructor(
    private val stepsPerHoursRepository: StepsPerHoursRepository
) {
//    suspend fun insertStepsPerHourToLocalDb(steps: Float) {
//        val stepsPerHour = StepsPerHour(
//            getCurrentHourId(),
//            getCurrentDayId(),
//            steps.toInt(),
//            steps.getCaloriesBurnedFromStepCount(),
//            steps.getMoveTimeFromStepCount()
//        )
//        stepsPerHoursRepository.insert(stepsPerHour)
//        Log.d(StepCountFragment.stepCountTag, "Insert steps per hour to local db")
//    }

//    suspend fun insertStepsPerHourToRemoteDb(steps: Float) {
//        val stepsPerHour = StepsPerHour(
//            getCurrentHourId(),
//            getCurrentDayId(),
//            steps.toInt(),
//            steps.getCaloriesBurnedFromStepCount(),
//            steps.getMoveTimeFromStepCount()
//        )
//        stepsPerHoursRepository.insertStepsPerHourToFirebase(stepsPerHour)
//        Log.d(StepCountFragment.stepCountTag, "Insert steps per hour to remote db")
//    }
}