package com.vn.wecare.feature.splash

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.feature.account.usecase.GetWecareUserWithIdUsecase
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.home.goal.data.CurrentGoalDailyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.CurrentGoalWeeklyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.usecase.GetGoalDailyRecordUsecase
import com.vn.wecare.feature.home.goal.usecase.GetGoalWeeklyRecordUsecase
import com.vn.wecare.feature.home.goal.usecase.GetGoalsFromFirebaseUsecase
import com.vn.wecare.feature.home.goal.usecase.GetTotalCaloriesIndexOfAGoalUsecase
import com.vn.wecare.feature.home.goal.usecase.UpdateGoalStatusUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SplashUiState(
    val saveUserRes: Response<Boolean>? = null
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getWecareUserWithIdUsecase: GetWecareUserWithIdUsecase,
    private val accountService: AccountService,
    private val getGoalFromFirebaseUsecase: GetGoalsFromFirebaseUsecase,
    private val getGoalWeeklyRecordUsecase: GetGoalWeeklyRecordUsecase,
    private val getGoalDailyRecordUsecase: GetGoalDailyRecordUsecase,
    private val getTotalCaloriesIndexOfAGoalUsecase: GetTotalCaloriesIndexOfAGoalUsecase,
    private val updateGoalStatusUsecase: UpdateGoalStatusUsecase,
) : ViewModel() {

    private val _splashUiState = MutableStateFlow(SplashUiState())
    val splashUiState = _splashUiState.asStateFlow()

    var shouldMoveToOnboarding by mutableStateOf(false)
    var shouldMoveToHomeScreen by mutableStateOf(false)

    fun resetResult() {
        shouldMoveToOnboarding = false
        shouldMoveToHomeScreen = false
        _splashUiState.update { it.copy(saveUserRes = null) }
    }

    fun hasUser() = accountService.hasUser

    fun saveNecessaryInformationToSingletonObject() = viewModelScope.launch {
        _splashUiState.update { it.copy(saveUserRes = Response.Loading) }
        combine(
            getWecareUserWithIdUsecase.getUserFromFirebaseWithId(accountService.currentUserId),
            getGoalFromFirebaseUsecase.getCurrentGoalFromFirebase()
        ) { user, goal ->
            if (user is Response.Success && user.data != null) {
                val res = user.data
                WecareUserSingletonObject.updateInstance(res)
                if (res.gender == null || res.age == null || res.height == null || res.weight == null || res.goal == null) {
                    shouldMoveToOnboarding = true
                }
            }
            if (goal is Response.Success) {
                LatestGoalSingletonObject.updateInStance(goal.data)
                Log.d(SplashFragment.splashFlowTag, "Latest goal is ${goal.data}")
                updateCurrentGoalWeeklyRecord(goal.data.goalId)
                if (goal.data.goalStatus == GoalStatus.INPROGRESS.value && System.currentTimeMillis() > goal.data.dateEndGoal) {
                    updateCurrentGoalStatus(goal.data)
                }
                WecareCaloriesObject.updateUserCaloriesAmount()
            } else shouldMoveToOnboarding = true
            val result =
                WecareUserSingletonObject.getInstance() != WecareUser() && LatestGoalSingletonObject.getInStance() != Goal()
            _splashUiState.update { it.copy(saveUserRes = Response.Success(result)) }
            result
        }.collect {
            shouldMoveToHomeScreen = it
        }
    }

    private fun updateCurrentGoalWeeklyRecord(goalId: String) = viewModelScope.launch {
        getGoalWeeklyRecordUsecase.getCurrentGoalWeeklyRecord(goalId).collect {
            if (it is Response.Success) {
                CurrentGoalWeeklyRecordSingletonObject.updateInstance(it.data.copy(status = GoalStatus.INPROGRESS.value))
                updateCurrentGoalDailyRecord(goalId, it.data)
                if (it.data.status == GoalStatus.NOTSTARTED.value) {
                    updateGoalStatusUsecase.updateGoalStatusForWeeklyGoal(
                        GoalStatus.INPROGRESS, it.data
                    ).collect()
                    updateGoalStatusUsecase.updatePreviousGoalWeeklyRecords(goalId)
                }
                Log.d(SplashFragment.splashFlowTag, "Latest weekly record is ${it.data}")
            } else CurrentGoalWeeklyRecordSingletonObject.updateInstance(
                GoalWeeklyRecord()
            )
        }
    }

    private fun updateCurrentGoalDailyRecord(goalId: String, weeklyRecord: GoalWeeklyRecord) =
        viewModelScope.launch {
            getGoalDailyRecordUsecase.getCurrentGoalDailyRecord(goalId, weeklyRecord).collect {
                if (it is Response.Success) {
                    CurrentGoalDailyRecordSingletonObject.updateInstance(it.data)
                    Log.d(SplashFragment.splashFlowTag, "Latest daily record is ${it.data}")
                } else CurrentGoalDailyRecordSingletonObject.updateInstance(
                    GoalDailyRecord()
                )
            }
        }

    private suspend fun updateCurrentGoalStatus(goal: Goal) {
        if (goal.goalName == EnumGoal.MAINTAINWEIGHT.value) {
            updateGoalStatusUsecase.update(goal.goalId, GoalStatus.DONE).collect()
        } else {
            combine(
                getTotalCaloriesIndexOfAGoalUsecase.getTotalCaloriesInIndex(goal.goalId),
                getTotalCaloriesIndexOfAGoalUsecase.getTotalCaloriesOutIndex(goal.goalId)
            ) { caloriesIn, caloriesOut ->
                updateGoalStatusUsecase.autoUpdateForGainMuscleAndLooseWeight(
                    goal, caloriesIn, caloriesOut
                )
            }.collect()
        }
    }
}