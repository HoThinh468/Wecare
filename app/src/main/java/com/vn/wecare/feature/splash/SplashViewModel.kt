package com.vn.wecare.feature.splash

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.usecase.GetWecareUserWithIdUsecase
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.food.WecareCaloriesObject
import com.vn.wecare.feature.home.goal.data.CurrentGoalDailyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.CurrentGoalWeeklyRecordSingletonObject
import com.vn.wecare.feature.home.goal.data.LatestGoalSingletonObject
import com.vn.wecare.feature.home.goal.data.model.Goal
import com.vn.wecare.feature.home.goal.data.model.GoalDailyRecord
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.feature.home.goal.usecase.GetGoalDailyRecordUsecase
import com.vn.wecare.feature.home.goal.usecase.GetGoalWeeklyRecordUsecase
import com.vn.wecare.feature.home.goal.usecase.GetGoalsFromFirebaseUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val getGoalDailyRecordUsecase: GetGoalDailyRecordUsecase
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

    fun hasUser(): Boolean {
        return accountService.hasUser
    }

    fun saveWecareUserToSingletonObject() = viewModelScope.launch {
        _splashUiState.update { it.copy(saveUserRes = Response.Loading) }
        getWecareUserWithIdUsecase.getUserFromRoomWithId(accountService.currentUserId)
            .collect { res ->
                Log.d(SplashFragment.splashFlowTag, "Get user from local db result: $res")
                if (res is Response.Success && res.data != null) {
                    WecareUserSingletonObject.updateInstance(res.data)
                    WecareCaloriesObject.updateUserCaloriesAmount()
                    checkIfAdditionalInformationMissing()
                    checkIfUserInformationIsUpdated()
                    updateGoalSingletonObject()
                    _splashUiState.update { it.copy(saveUserRes = Response.Success(true)) }
                } else {
                    _splashUiState.update { it.copy(saveUserRes = Response.Error(null)) }
                }
            }
    }

    private fun checkIfAdditionalInformationMissing() {
        val res = WecareUserSingletonObject.getInstance()
        if (res.gender == null || res.age == null || res.height == null || res.weight == null || res.goal == null) {
            shouldMoveToOnboarding = true
        }
    }

    private fun checkIfUserInformationIsUpdated() {
        val it = WecareUserSingletonObject.getInstance()
        if (it.userId.isNotEmpty()) {
            shouldMoveToHomeScreen = true
        }
    }

    private fun updateGoalSingletonObject() = viewModelScope.launch {
        getGoalFromFirebaseUsecase.getCurrentGoalFromFirebase().collect {
            if (it is Response.Success) {
                LatestGoalSingletonObject.updateInStance(it.data)
                updateCurrentGoalWeeklyRecord(it.data.goalId)
                Log.d(SplashFragment.splashFlowTag, "Latest goal is ${it.data}")
            } else LatestGoalSingletonObject.updateInStance(Goal())
        }
    }

    private fun updateCurrentGoalWeeklyRecord(goalId: String) = viewModelScope.launch {
        getGoalWeeklyRecordUsecase.getCurrentGoalWeeklyRecord(goalId).collect {
            if (it is Response.Success) {
                CurrentGoalWeeklyRecordSingletonObject.updateInstance(it.data)
                updateCurrentGoalDailyRecord(goalId, it.data)
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
}