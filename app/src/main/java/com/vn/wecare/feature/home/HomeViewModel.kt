package com.vn.wecare.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingleton
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.step_count.alarm.StepCountExactAlarms
import com.vn.wecare.feature.home.step_count.getCaloriesBurnedFromStepCount
import com.vn.wecare.feature.home.step_count.getMoveTimeFromStepCount
import com.vn.wecare.feature.home.step_count.usecase.GetCurrentStepsFromSensorUsecase
import com.vn.wecare.feature.home.step_count.usecase.GetStepsPerDayUsecase
import com.vn.wecare.feature.home.water.data.model.WaterRecordEntity
import com.vn.wecare.feature.home.water.tracker.usecase.GetWaterRecordListUsecase
import com.vn.wecare.feature.home.water.tracker.usecase.WaterRecordUsecase
import com.vn.wecare.utils.getDayId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.pow

data class HomeUiState(
    val stepCount: Int = 0,
    val caloriesBurnt: Int = 0,
    val timeConsumed: Int = 0,
    val bmiIndex: Float = 0f,
    val waterIndex: Int = 0,
    val waterTargetAmount: Int = 0,
    val waterRecord: List<WaterRecordEntity> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val stepCountInExactAlarms: InExactAlarms,
    private val stepCountExactAlarms: StepCountExactAlarms,
    private val getStepsPerDayUsecase: GetStepsPerDayUsecase,
    private val getCurrentStepsFromSensorUsecase: GetCurrentStepsFromSensorUsecase,
    private val getWaterRecordListUsecase: GetWaterRecordListUsecase,
    private val waterRecordUsecase: WaterRecordUsecase
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    fun initHomeUIState() {
        updateCurrentSteps(getCurrentStepsFromSensorUsecase.getCurrentStepsFromSensor())
        updateBMIInformation()
        updateWaterAmountDrankInDay()
        getWaterTarget()
    }

    fun cancelInExactAlarm() {
        stepCountInExactAlarms.clearInExactAlarm()
    }

    fun clearExactAlarm() {
        stepCountExactAlarms.clearExactAlarm()
    }

    fun updateCurrentSteps(stepsFromSensor: Float) = viewModelScope.launch {
        getStepsPerDayUsecase.getCurrentDaySteps(stepsFromSensor).collect { steps ->
            _homeUiState.update {
                it.copy(
                    stepCount = steps.toInt(),
                    caloriesBurnt = steps.getCaloriesBurnedFromStepCount(),
                    timeConsumed = steps.getMoveTimeFromStepCount(),
                )
            }
        }
    }

    private fun updateBMIInformation() = viewModelScope.launch {
        WecareUserSingleton.getInstanceFlow().collect { user ->
            _homeUiState.update {
                it.copy(
                    bmiIndex = calculateBMI(user.weight ?: 30, user.height ?: 130),
                )
            }
        }
    }

    private fun calculateBMI(weight: Int, height: Int): Float {
        return (weight.toFloat() / (height.toFloat() / 100).pow(2))
    }

    private fun updateWaterAmountDrankInDay() = viewModelScope.launch {
        _homeUiState.update { it.copy(waterIndex = 0, waterRecord = emptyList()) }
        getWaterRecordListUsecase.getCurrentDay().collect { response ->
            if (response is Response.Success && response.data != null) {
                repeat(response.data.size) { i ->
                    _homeUiState.update {
                        it.copy(
                            waterIndex = it.waterIndex + response.data[i].amount,
                            waterRecord = response.data
                        )
                    }
                }
            } else {
                _homeUiState.update { it.copy(waterIndex = 0) }
            }
        }
    }

    private fun getWaterTarget() = viewModelScope.launch {
        WecareUserSingleton.getInstanceFlow().collect { user ->
            _homeUiState.update {
                it.copy(waterTargetAmount = (user.weight?.times(30) ?: 2000f).toInt())
            }
        }
    }

    fun addNewWaterRecord() = viewModelScope.launch {
        val currentTime = Calendar.getInstance()
        val newRecord = WaterRecordEntity(
            recordId = generateRecordId(currentTime),
            amount = 250,
            dateTime = currentTime,
            userId = WecareUserSingleton.getInstance().userId,
            dayId = getDayId(
                currentTime.get(Calendar.DAY_OF_MONTH),
                currentTime.get(Calendar.MONTH),
                currentTime.get(Calendar.YEAR)
            ),
            currentTarget = _homeUiState.value.waterTargetAmount
        )
        waterRecordUsecase.insertNewRecord(newRecord)
        updateWaterAmountDrankInDay()
    }

    fun deleteLatestRecord() = viewModelScope.launch {
        val latestRecord = _homeUiState.value.waterRecord.last()
        waterRecordUsecase.deleteRecord(latestRecord)
        updateWaterAmountDrankInDay()
    }

    private fun generateRecordId(time: Calendar): String {
        val year = time.get(Calendar.YEAR)
        val month = time.get(Calendar.MONTH)
        val day = time.get(Calendar.DAY_OF_MONTH)
        val hour = time.get(Calendar.HOUR_OF_DAY)
        val minute = time.get(Calendar.MINUTE)
        val second = time.get(Calendar.SECOND)
        return "${year}_${month}_${day}_${hour}_${minute}_${second}"
    }
}