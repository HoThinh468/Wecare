package com.vn.wecare.feature.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingleton
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.feature.account.usecase.GetWecareUserWithIdUsecase
import com.vn.wecare.feature.authentication.service.AccountService
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

    var shouldMoveToAuthentication by mutableStateOf(false)
    var shouldMoveToOnboarding by mutableStateOf(false)

    fun resetResult() {
        shouldMoveToOnboarding = false
        shouldMoveToAuthentication = false
        _splashUiState.update { it.copy(saveUserRes = null) }
    }

    fun hasUser(): Boolean {
        val hasUser = accountService.hasUser
        shouldMoveToAuthentication = !hasUser
        return hasUser
    }

    fun saveWecareUserToSingletonObject() = viewModelScope.launch {
        _splashUiState.update { it.copy(saveUserRes = Response.Loading) }
        getWecareUserWithIdUsecase.getUserFromRoomWithId(accountService.currentUserId)
            .collect { res ->
                if (res is Response.Success && res.data != null) {
                    WecareUserSingleton.updateInstance(res.data)
                    checkIfAdditionalInformationMissing(res.data)
                    _splashUiState.update { it.copy(saveUserRes = Response.Success(true)) }
                } else _splashUiState.update { it.copy(saveUserRes = Response.Error(null)) }
            }

    }

    private fun checkIfAdditionalInformationMissing(res: WecareUser) {
        if (res.gender == null || res.age == null || res.height == null || res.weight == null || res.goal == null) {
            shouldMoveToOnboarding = true
        }
    }
}