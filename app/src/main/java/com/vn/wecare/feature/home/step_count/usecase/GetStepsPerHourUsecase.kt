package com.vn.wecare.feature.home.step_count.usecase

import android.util.Log
import com.vn.wecare.feature.home.step_count.data.repository.StepsPerHoursRepository
import com.vn.wecare.utils.getCurrentDayId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetStepsPerHourUsecase @Inject constructor(
    private val stepsPerHoursRepository: StepsPerHoursRepository
){
    private fun getTotalStepsInDayWithDayId(dayId: String) : Float {
        var stepsInDay = 0f
        stepsPerHoursRepository.getStepsPerDayWithHours(dayId)?.map {
            it.forEach() { stepsPerDayWithHours ->
                stepsInDay += stepsPerDayWithHours.hour.steps
            }
        }?.catch {
            Log.d("Steps per hour error", "Cannot get steps per day with hours")
        }
        return stepsInDay
    }

    fun getCurrentHourSteps(currentDaySteps: Float) : Flow<Float> = flow {
        val currentHourSteps = currentDaySteps - getTotalStepsInDayWithDayId(getCurrentDayId())
        emit(currentHourSteps)
    }
}