package com.vn.wecare.feature.account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.home.goal.dashboard.GoalDetailUiState
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.usecase.GetGoalWeeklyRecordUsecase
import com.vn.wecare.feature.home.goal.usecase.GetGoalsFromFirebaseUsecase
import com.vn.wecare.utils.WecareUserConstantValues
import com.vn.wecare.utils.getProgressInFloatWithIntInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

data class GoalFilterUiState(
    val getDataResponse: Response<Boolean>? = null, val time: Long = 0
)

@HiltViewModel
class GoalHistoryViewModel @Inject constructor(
    private val getGoalsFromFirebaseUsecase: GetGoalsFromFirebaseUsecase,
    private val getGoalWeeklyRecordUsecase: GetGoalWeeklyRecordUsecase
) : ViewModel() {

    private val _goals = MutableStateFlow(emptyList<Goal>())
    val goals = _goals.asStateFlow()

    private val _goalWeeklyRecords = MutableStateFlow(emptyList<GoalWeeklyRecord>())
    val goalWeeklyRecord = _goalWeeklyRecords.asStateFlow()

    private val _currentChosenGoal = MutableStateFlow(Goal())
    val currentChosenGoal = _currentChosenGoal.asStateFlow()

    private val _detailUi = MutableStateFlow(GoalDetailUiState())
    val detailUi = _detailUi.asStateFlow()

    private val _isResetEnabled = MutableStateFlow(false)
    val isResetEnabled = _isResetEnabled.asStateFlow()


    fun initGoalHistoryUi() {
        getGoalsFromFirebase()
        checkIfResetIsEnabled()
    }

    fun onGoalSelected(goal: Goal) {
        _currentChosenGoal.update { goal }
        initDetailUi(goal)
    }

    private fun initDetailUi(goal: Goal) {
        _detailUi.update {
            it.copy(
                goalName = goal.goalName,
                description = getGoalDescription(
                    goal.goalName, goal.weightDifference, goal.timeToReachGoalInWeek
                ),
                dayLeft = getDayLeft(goal.dateEndGoal),
                timeProgress = getTimeProgress(goal.dateEndGoal, goal.dateSetGoal),
                caloriesInGoal = goal.caloriesInEachDayGoal,
                caloriesOutGoal = goal.caloriesBurnedEachDayGoal,
                caloriesRecommend = goal.caloriesBurnedGoalForStepCount,
                stepRecommend = goal.stepsGoal,
                activeTimeRecommend = goal.moveTimeGoal,
                status = goal.goalStatus,
                weightToLose = goal.weightDifference,
                timeToReachGoal = goal.timeToReachGoalInWeek,
                weeklyGoalWeight = goal.weeklyGoalWeight
            )
        }
    }

    private fun getGoalDescription(name: String, weightDiffer: Int, time: Int): String {
        return when (name) {
            EnumGoal.GAINWEIGHT.value -> "You want to gain $weightDiffer kg in $time week(s)"
            EnumGoal.LOSEWEIGHT.value -> "You want to loose $weightDiffer kg in $time week(s)"
            else -> "$name in $time week(s)"
        }
    }

    private fun getDayLeft(endDay: Long): Int {
        return (abs(endDay - System.currentTimeMillis()) / WecareUserConstantValues.DAY_TO_MILLISECONDS).toInt()
    }

    private fun getTimeProgress(endDay: Long, startDay: Long): Float {
        val totalDay = ((endDay - startDay) / WecareUserConstantValues.DAY_TO_MILLISECONDS).toInt()
        val dayPassed = totalDay - getDayLeft(endDay)
        return getProgressInFloatWithIntInput(dayPassed, totalDay)
    }

    private fun getGoalsFromFirebase() = viewModelScope.launch {
        getGoalsFromFirebaseUsecase.getDoneGoals().collect { res ->
            if (res is Response.Success) {
                _goals.update { res.data }
            }
        }
    }

    fun getRecords(goal: Goal) = viewModelScope.launch {
        getGoalWeeklyRecordUsecase.getAll(goal.goalId).collect { res ->
            if (res is Response.Success) {
                _goalWeeklyRecords.update { res.data }
            }
        }
    }

    private fun checkIfResetIsEnabled() {

    }
}