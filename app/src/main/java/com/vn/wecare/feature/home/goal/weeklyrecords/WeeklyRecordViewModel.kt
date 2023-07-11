package com.vn.wecare.feature.home.goal.weeklyrecords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.usecase.GetGoalDailyRecordUsecase
import com.vn.wecare.feature.home.goal.utils.generateGoalWeeklyRecordIdWithGoal
import com.vn.wecare.feature.home.goal.utils.getDayFromLongWithFormat2
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK
import com.vn.wecare.utils.getProgressInFloatWithIntInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WeekRecordDetailUiState(
    val startDay: String = "",
    val endDay: String = "",
    val totalRecords: Int = 0,
    val progress: Float = 0f,
    val caloriesIn: Int = 0,
    val caloriesOut: Int = 0,
    val caloriesDifference: Int = 0,
    val weightDifference: Float = 0f,
    val records: List<GoalDailyRecord> = emptyList()
)

@HiltViewModel
class WeeklyRecordViewModel @Inject constructor(
    private val getGoalDailyRecordUsecase: GetGoalDailyRecordUsecase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeekRecordDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun initUiState(record: GoalWeeklyRecord) {
        val caloriesDifference = kotlin.math.abs(record.caloriesIn - record.caloriesOut)
        _uiState.update {
            it.copy(
                startDay = getDayFromLongWithFormat2(record.startDate),
                endDay = getDayFromLongWithFormat2(record.endDate),
                caloriesIn = record.caloriesIn,
                caloriesOut = record.caloriesOut,
                caloriesDifference = caloriesDifference,
                weightDifference = (caloriesDifference.toFloat() / 7700f),
            )
        }
        getAllDayRecordsInTheWeek(generateGoalWeeklyRecordIdWithGoal(record))
    }

    private fun getAllDayRecordsInTheWeek(weekId: String) = viewModelScope.launch {
        getGoalDailyRecordUsecase.getAllDailyRecordOfAWeek(
            LatestGoalSingletonObject.getInStance().goalId, weekId
        ).collect { res ->
            if (res is Response.Success) {
                _uiState.update {
                    it.copy(
                        totalRecords = res.data.size,
                        records = res.data,
                        progress = getProgressInFloatWithIntInput(
                            res.data.size, NUMBER_OF_DAYS_IN_WEEK
                        )
                    )
                }
            }
        }
    }
}