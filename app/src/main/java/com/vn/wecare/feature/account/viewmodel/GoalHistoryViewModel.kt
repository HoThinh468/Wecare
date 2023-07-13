package com.vn.wecare.feature.account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.usecase.GetGoalWeeklyRecordUsecase
import com.vn.wecare.feature.home.goal.usecase.GetGoalsFromFirebaseUsecase
import com.vn.wecare.utils.WecareUserConstantValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GoalFilterUiState(
    val getDataResponse: Response<Boolean>? = null, val time: Long = 0
)

data class GoalDetailUiState(
    val getRecordsResponse: Response<Boolean>? = null,
    val records: List<GoalWeeklyRecord> = emptyList(),
    val totalCaloIn: Int = 0,
    val totalCaloOut: Int = 0,
    val caloriesRecommendation: Int = 0,
    val isResetGoalEnable: Boolean = false
)

@HiltViewModel
class GoalHistoryViewModel @Inject constructor(
    private val getGoalsFromFirebaseUsecase: GetGoalsFromFirebaseUsecase,
    private val getGoalWeeklyRecordUsecase: GetGoalWeeklyRecordUsecase
) : ViewModel() {

    private val _goals = MutableStateFlow(emptyList<Goal>())
    val goals = _goals.asStateFlow()

    private val _currentChosenGoal = MutableStateFlow(Goal())
    val currentChosenGoal = _currentChosenGoal.asStateFlow()

    private val _detailUiState = MutableStateFlow(GoalDetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    private val _filterUi = MutableStateFlow(GoalFilterUiState())
    val filterUi = _filterUi.asStateFlow()

    fun initGoalHistoryUi() {
        getGoalsFromFirebase()
        checkIfResetIsEnabled()
    }

    fun onGoalSelected(goal: Goal) {
        _currentChosenGoal.update { goal }
    }

    fun resetGetRecordResponse() {
        _detailUiState.update { it.copy(getRecordsResponse = null) }
    }

    private fun getGoalsFromFirebase() = viewModelScope.launch {
        _filterUi.update { it.copy(getDataResponse = Response.Loading) }
        getGoalsFromFirebaseUsecase.getGoalsFromFirebase().collect { res ->
            if (res is Response.Success) {
                _goals.update { res.data }
                _filterUi.update { it.copy(getDataResponse = Response.Success(true)) }
            } else {
                _filterUi.update { it.copy(getDataResponse = Response.Error(Exception("Fail to load data!"))) }
            }
        }
    }

    fun getRecords(goal: Goal) = viewModelScope.launch {
        _detailUiState.update { it.copy(getRecordsResponse = Response.Loading) }
        getGoalWeeklyRecordUsecase.getAllRecord(goal.goalId).collect { res ->
            if (res is Response.Success) {
                _detailUiState.update {
                    it.copy(
                        records = res.data
                    )
                }
                _detailUiState.update { it.copy(getRecordsResponse = Response.Success(true)) }
                updateCaloriesInfo(res.data)
            } else {
                _detailUiState.update {
                    it.copy(
                        getRecordsResponse = Response.Error(
                            java.lang.Exception(
                                "Fail to get data!"
                            )
                        )
                    )
                }
            }
        }
    }

    private fun updateCaloriesInfo(res: List<GoalWeeklyRecord>) {
        var totalCaloriesIn = 0
        var totalCaloriesOut = 0
        for (i in res) {
            totalCaloriesIn += i.caloriesIn
            totalCaloriesOut += i.caloriesOut
        }
        val caloriesRecommendation = when (_currentChosenGoal.value.goalName) {
            EnumGoal.GAINMUSCLE.value -> WecareUserConstantValues.DEFAULT_CALORIES_TO_BURN_EACH_DAY_TO_GAIN_MUSCLE
            EnumGoal.LOSEWEIGHT.value -> WecareUserConstantValues.DEFAULT_CALORIES_TO_BURN_EACH_DAY_TO_LOSE_WEIGHT
            else -> WecareUserConstantValues.DEFAULT_CALORIES_TO_BURN_EACH_DAY
        }
        _detailUiState.update {
            it.copy(
                totalCaloIn = totalCaloriesIn,
                totalCaloOut = totalCaloriesOut,
                caloriesRecommendation = caloriesRecommendation
            )
        }
    }

    private fun checkIfResetIsEnabled() {
        _detailUiState.update {
            it.copy(
                isResetGoalEnable = LatestGoalSingletonObject.getInStance().goalStatus != GoalStatus.INPROGRESS.value
            )
        }
    }
}