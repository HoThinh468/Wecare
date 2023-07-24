package com.vn.wecare.feature.home.goal.weeklyrecords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.CurrentGoalWeeklyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.usecase.GetGoalDailyRecordUsecase
import com.vn.wecare.feature.home.goal.utils.generateGoalWeeklyRecordIdWithGoal
import com.vn.wecare.utils.WecareUserConstantValues.DAY_TO_MILLISECONDS
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK
import com.vn.wecare.utils.getDayOfWeekInStringWithLong
import com.vn.wecare.utils.getProgressInFloatWithIntInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WeekRecordDetailUiState(
    val getRecordsResponse: Response<Boolean>? = null,
    val progress: Float = 0f,
    val records: List<GoalDailyRecord> = emptyList(),
    val averageCaloriesInEachDay: Int = 0,
    val averageCaloriesOutEachDay: Int = 0,
    val totalCaloriesIn: Int = 0,
    val totalCaloriesOut: Int = 0,
    val totalCaloriesOutWithBmr: Int = 0,
    val completedDays: Int = 0,
    val recordedDays: Int = 0,
)

@HiltViewModel
class WeeklyRecordViewModel @Inject constructor(
    private val getGoalDailyRecordUsecase: GetGoalDailyRecordUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeekRecordDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun initUiState(record: GoalWeeklyRecord) {
        _uiState.update { it.copy(records = getBaseRecordList(record)) }
        getAllDayRecordsInTheWeek(generateGoalWeeklyRecordIdWithGoal(record))
    }

    private fun getBaseRecordList(record: GoalWeeklyRecord): List<GoalDailyRecord> {
        val baseList = arrayListOf<GoalDailyRecord>()
        for (i in 0..6) {
            val dayInLong = record.startDate + (i * DAY_TO_MILLISECONDS)
            val dayRecord = GoalDailyRecord(dayInLong = dayInLong)
            baseList.add(dayRecord)
        }
        updateTotalCaloriesOutWithBMR(
            bmr = record.bmr,
            startDay = record.startDate,
            caloriesOutOnlyActivity = record.caloriesOut
        )
        return baseList
    }

    private fun getAllDayRecordsInTheWeek(weekId: String) = viewModelScope.launch {
        _uiState.update { it.copy(getRecordsResponse = Response.Loading) }
        getGoalDailyRecordUsecase.getAllDailyRecordOfAWeek(
            LatestGoalSingletonObject.getInStance().goalId, weekId
        ).collect { res ->
            if (res is Response.Success) {
                _uiState.update {
                    it.copy(
                        progress = getProgressInFloatWithIntInput(
                            res.data.size, NUMBER_OF_DAYS_IN_WEEK
                        ), getRecordsResponse = Response.Success(true), recordedDays = res.data.size
                    )
                }
                updateRecordsAfterGetData(res.data)
            } else {
                _uiState.update { it.copy(getRecordsResponse = Response.Error(Exception("Fail to load daily records!"))) }
            }
        }
    }

    private fun updateRecordsAfterGetData(result: List<GoalDailyRecord>) {
        val baseList =
            getBaseRecordList(CurrentGoalWeeklyRecordSingletonObject.getInstance()).toMutableList()
        for (item in result) {
            baseList.filter {
                getDayOfWeekInStringWithLong(it.dayInLong) == getDayOfWeekInStringWithLong(item.dayInLong)
            }.forEach {
                val index = baseList.indexOf(it)
                baseList[index] = item
            }
        }
        updateCaloriesIndex(baseList)
        updateCompletedDays(baseList)
        _uiState.update { it.copy(records = baseList) }
    }

    private fun updateCaloriesIndex(res: List<GoalDailyRecord>) {
        var totalCalories = 0
        var totalCaloriesOut = 0
        for (i in res) {
            totalCalories += i.caloriesIn
            totalCaloriesOut += i.caloriesOut
        }
        _uiState.update {
            it.copy(
                totalCaloriesIn = totalCalories,
                totalCaloriesOut = totalCaloriesOut,
                averageCaloriesInEachDay = totalCalories / NUMBER_OF_DAYS_IN_WEEK,
                averageCaloriesOutEachDay = totalCaloriesOut / NUMBER_OF_DAYS_IN_WEEK
            )
        }
    }

    private fun updateCompletedDays(res: List<GoalDailyRecord>) {
        var completedDays = 0
        for (i in res) {
            if (i.caloriesIn >= i.goalDailyCalories) completedDays++
        }
        _uiState.update { it.copy(completedDays = completedDays) }
    }

    private fun updateTotalCaloriesOutWithBMR(
        bmr: Int, startDay: Long, caloriesOutOnlyActivity: Int
    ) {
        val dayPass = (System.currentTimeMillis() - startDay) / DAY_TO_MILLISECONDS
        _uiState.update {
            it.copy(totalCaloriesOutWithBmr = (bmr * dayPass.toInt()) + caloriesOutOnlyActivity)
        }
    }
}