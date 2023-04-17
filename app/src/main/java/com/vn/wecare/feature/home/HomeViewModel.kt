package com.vn.wecare.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.WecareUserSingleton
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.feature.account.usecase.GetWecareUserWithIdUsecase
import com.vn.wecare.feature.authentication.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val hasUser: Boolean = false, val isAdditionInfoMissing: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWecareUserWithIdUsecase: GetWecareUserWithIdUsecase,
    private val accountService: AccountService,
    private val stepCountInExactAlarms: InExactAlarms,
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUIState = _homeUiState.asStateFlow()

    init {
        if (!checkIfUserIsNull()) {
            saveWecareUserToSingletonObject()
        }
    }

    private fun saveWecareUserToSingletonObject() = viewModelScope.launch {
        getWecareUserWithIdUsecase.getUserFromRoomWithId(accountService.currentUserId)
            .collect { res ->
                if (res is Response.Success) {
                    res.data?.let {
                        WecareUserSingleton.updateInstance(it)
                        checkIfAdditionalInformationMissing(it)
                    }
                }
            }
    }

    private fun checkIfAdditionalInformationMissing(res: WecareUser) {
        if (res.gender == null || res.age == null || res.height == null || res.weight == null || res.goal == null) {
            _homeUiState.update { it.copy(isAdditionInfoMissing = true) }
        }
    }

    private fun checkIfUserIsNull(): Boolean {
        val hasUser = accountService.hasUser
        _homeUiState.update {
            it.copy(hasUser = hasUser)
        }
        return !hasUser
    }

    fun resetUserNull() {
        _homeUiState.update { it.copy(hasUser = false) }
    }

    fun resetUserAdditionalInformationRes() {
        _homeUiState.update { it.copy(isAdditionInfoMissing = false) }
    }

    fun cancelInExactAlarm() {
        stepCountInExactAlarms.clearInExactAlarm()
    }
}