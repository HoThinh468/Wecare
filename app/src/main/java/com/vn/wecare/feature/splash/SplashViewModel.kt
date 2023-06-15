package com.vn.wecare.feature.splash

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingleton
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.usecase.GetWecareUserWithIdUsecase
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.food.WecareCaloriesObject
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
    private val accountService: AccountService
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
                    WecareUserSingleton.updateInstance(res.data)
                    WecareCaloriesObject.calculateUserCaloriesAmount()
                    checkIfAdditionalInformationMissing()
                    checkIfUserInformationIsUpdated()
                    _splashUiState.update { it.copy(saveUserRes = Response.Success(true)) }
                } else {
                    _splashUiState.update { it.copy(saveUserRes = Response.Error(null)) }
                }
            }
    }

    private fun checkIfAdditionalInformationMissing() {
        val res = WecareUserSingleton.getInstance()
        if (res.gender == null || res.age == null || res.height == null || res.weight == null || res.goal == null) {
            shouldMoveToOnboarding = true
        }
    }

    private fun checkIfUserInformationIsUpdated() {
        val it = WecareUserSingleton.getInstance()
        if (it.userId.isNotEmpty()) {
            shouldMoveToHomeScreen = true
        }
    }
}