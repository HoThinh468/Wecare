package com.vn.wecare.feature.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.usecase.ClearSharedPreferencesUsecase
import com.vn.wecare.feature.account.usecase.DeleteWecareUserUsecase
import com.vn.wecare.feature.account.usecase.GetWecareUserWithIdUsecase
import com.vn.wecare.feature.account.view.AccountFragment.Companion.AccountFlowTAG
import com.vn.wecare.feature.authentication.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AccountUiState(
    val username: String = "",
    val email: String = "",
    val signOutResponse: Response<Boolean>? = null,
    val isEmailVerified: Boolean = false,
)

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountService: AccountService,
    private val clearSharedPreferencesUsecase: ClearSharedPreferencesUsecase,
    private val stepCountExactAlarms: ExactAlarms,
    private val stepCountInExactAlarms: InExactAlarms,
    private val deleteWecareUserUsecase: DeleteWecareUserUsecase,
    private val getWecareUserWithIdUsecase: GetWecareUserWithIdUsecase
) : ViewModel() {

    private val _accountUiState = MutableStateFlow(AccountUiState())
    val accountUiState = _accountUiState.asStateFlow()

    val currentUserId = accountService.currentUserId

    init {
        updateAccountScreen()
    }

    private fun updateAccountScreen() = viewModelScope.launch {
        val user = getWecareUserWithIdUsecase.getUserFromRoomWithId(accountService.currentUserId)
        user.collect { res ->
            if (res is Response.Success && res.data != null) {
                _accountUiState.update {
                    it.copy(
                        username = res.data.userName,
                        email = res.data.email,
                        isEmailVerified = res.data.isEmailVerified
                    )
                }
            }
        }
    }

    fun onSignOutClick() = viewModelScope.launch {
        Log.d(AccountFlowTAG, "Signing out user with id: ${accountService.currentUserId}")
        _accountUiState.update { it.copy(signOutResponse = Response.Loading) }
        _accountUiState.update { it.copy(signOutResponse = accountService.signOut()) }
    }

    fun sendVerificationEmail() = viewModelScope.launch {
        accountService.sendVerificationEmail()
    }

    fun handleSignOutSuccess(moveToAuthenticationGraph: () -> Unit) {
        Log.d(AccountFlowTAG, "Sign out success, start delete user from local db")
        moveToAuthenticationGraph()
        clearSharedPreferencesUsecase.clearAllSharedPref()
        stepCountExactAlarms.clearExactAlarm()
        stepCountInExactAlarms.clearInExactAlarm()
        viewModelScope.launch {
            getWecareUserWithIdUsecase.getUserFromRoomWithId(currentUserId).collect {
                Log.d(AccountFlowTAG, "Sign out user response: $it")
                if (it is Response.Success && it.data != null) {
                    deleteWecareUserUsecase.deleteAccount(
                        it.data.userId, it.data.userName, it.data.email, it.data.isEmailVerified
                    )
                }
            }
        }
        clearSignOutResponse()
    }

    fun handleSignOutError() {
        Log.d(AccountFlowTAG, "Sign out fail, cannot delete user from local db")
        clearSignOutResponse()
    }

    private fun clearSignOutResponse() {
        _accountUiState.update {
            it.copy(
                signOutResponse = null
            )
        }
    }
}