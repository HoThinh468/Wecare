package com.vn.wecare.feature.account.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.R
import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.feature.account.usecase.ClearSharedPreferencesUsecase
import com.vn.wecare.feature.account.usecase.DeleteWecareUserUsecase
import com.vn.wecare.feature.account.usecase.GetWecareUserWithIdUsecase
import com.vn.wecare.feature.account.view.main.AccountFragment.Companion.AccountFlowTAG
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.water.tracker.usecase.DeleteAllLocalWaterRecordUsecase
import com.vn.wecare.utils.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

data class AccountUiState(
    val username: String = "",
    val email: String = "",
    val signOutResponse: Response<Boolean>? = null,
    val isEmailVerified: Boolean = false,
    val userNameLogo: String = "",
    val isReAuthenticateDialogShow: Boolean = false
)

data class ChangePasswordUiState(
    val isPasswordValid: Boolean = true,
    val isPasswordShow: Boolean = false,
    val password: String = "",
    val reAuthenticateResult: Response<Boolean>? = null,
    val isChangePasswordDialogShow: Boolean = false,
    val changePasswordResult: Response<Boolean>? = null
)

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountService: AccountService,
    private val clearSharedPreferencesUsecase: ClearSharedPreferencesUsecase,
    private val stepCountExactAlarms: ExactAlarms,
    private val stepCountInExactAlarms: InExactAlarms,
    private val deleteWecareUserUsecase: DeleteWecareUserUsecase,
    private val getWecareUserWithIdUsecase: GetWecareUserWithIdUsecase,
    private val deleteAllWaterRecordsUsecase: DeleteAllLocalWaterRecordUsecase
) : ViewModel() {

    private val _accountUiState = MutableStateFlow(AccountUiState())
    val accountUiState = _accountUiState.asStateFlow()

    private val _changePasswordUiState = MutableStateFlow(ChangePasswordUiState())
    val changePasswordUiState = _changePasswordUiState.asStateFlow()

    val currentUserId = accountService.currentUserId

    fun updateAccountScreen() = viewModelScope.launch {
        getWecareUserWithIdUsecase.getUserFromRoomWithId(accountService.currentUserId)
            .collect { res ->
                Log.d("User from room", "$res")
                if (res is Response.Success && res.data != null) {
                    _accountUiState.update {
                        it.copy(
                            username = res.data.userName,
                            email = res.data.email,
                            isEmailVerified = res.data.emailVerified,
                            userNameLogo = res.data.userName[0].uppercase(),
                        )
                    }
                }
            }
    }

    fun onChangePasswordClick() {
        _accountUiState.update {
            it.copy(isReAuthenticateDialogShow = true)
        }
    }

    fun onDismissReAuthenticateDialog() {
        _accountUiState.update {
            it.copy(isReAuthenticateDialogShow = false)
        }
    }

    fun onSignOutClick() = viewModelScope.launch {
        Log.d(AccountFlowTAG, "Signing out user with id: ${accountService.currentUserId}")
        _accountUiState.update { it.copy(signOutResponse = Response.Loading) }
        try {
            val signOutRes = accountService.signOut()
            if (signOutRes is Response.Success) {
                _accountUiState.update { it.copy(signOutResponse = accountService.signOut()) }
                WecareUserSingletonObject.updateInstance(null)
            } else {
                _accountUiState.update { it.copy(signOutResponse = Response.Error(Exception("Fail to log out!"))) }
            }
        } catch (e: Exception) {
            _accountUiState.update { it.copy(signOutResponse = Response.Error(e)) }
        }
    }

    fun sendVerificationEmail() = viewModelScope.launch {
        accountService.sendVerificationEmail().collect {
            if (it is Response.Success) {
                accountService.signOut()
            }
        }
    }

    fun handleSignOutSuccess(moveToAuthenticationGraph: () -> Unit) {
        moveToAuthenticationGraph()
        clearSharedPreferencesUsecase.clearAllSharedPref()
        stepCountExactAlarms.clearExactAlarm()
        stepCountInExactAlarms.clearInExactAlarm()
        viewModelScope.launch {
            getWecareUserWithIdUsecase.getUserFromRoomWithId(currentUserId).collect {
                Log.d(AccountFlowTAG, "Sign out user response: $it")
                if (it is Response.Success && it.data != null) {
                    deleteWecareUserUsecase.deleteAccount(
                        it.data.userId, it.data.userName, it.data.email, it.data.emailVerified
                    )
                }
            }
            deleteAllWaterRecordsUsecase.deleteAllRecords()
        }
        WecareUserSingletonObject.updateInstance(WecareUser())
        clearAccountUIState()
    }

    fun handleSignOutError() {
        Log.d(AccountFlowTAG, "Sign out fail, cannot delete user from local db")
        _accountUiState.update {
            it.copy(signOutResponse = null)
        }
    }

    fun onShowPasswordClick() {
        _changePasswordUiState.update {
            it.copy(isPasswordShow = !it.isPasswordShow)
        }
    }

    fun onSubmitReAuthenticationPassword() = viewModelScope.launch {
        checkValidPassword()
        if (_changePasswordUiState.value.isPasswordValid) {
            _changePasswordUiState.update { it.copy(reAuthenticateResult = Response.Loading) }
            _changePasswordUiState.update {
                it.copy(
                    reAuthenticateResult = accountService.reAuthenticateUser(
                        _changePasswordUiState.value.password
                    )
                )
            }
        }
    }

    fun clearReAuthenticateResult() {
        _changePasswordUiState.update {
            it.copy(reAuthenticateResult = null)
        }
    }

    private fun checkValidPassword() {
        _changePasswordUiState.update {
            it.copy(isPasswordValid = it.password.isValidPassword())
        }
    }

    fun onPasswordChange(newVal: String) {
        _changePasswordUiState.update {
            it.copy(password = newVal)
        }
    }

    fun getPasswordErrorMessage(): Int? = if (_changePasswordUiState.value.isPasswordValid) null
    else R.string.password_error_message

    fun onDismissChangePasswordDialog() {
        _changePasswordUiState.update {
            it.copy(isChangePasswordDialogShow = false)
        }
    }

    private fun clearAccountUIState() {
        _accountUiState.update {
            it.copy(
                signOutResponse = null,
                userNameLogo = "",
                username = "",
                email = "",
                isEmailVerified = false,
            )
        }
    }

    fun handleReAuthenticateSuccessful() {
        showChangePasswordDialog()
        clearReAuthenticateInfo()
    }

    private fun showChangePasswordDialog() {
        _changePasswordUiState.update {
            it.copy(isChangePasswordDialogShow = true)
        }
    }

    fun onSubmitChangePasswordClick() = viewModelScope.launch {
        checkValidPassword()
        if (_changePasswordUiState.value.isPasswordValid) {
            _changePasswordUiState.update { it.copy(changePasswordResult = Response.Loading) }
            _changePasswordUiState.update {
                it.copy(
                    changePasswordResult = accountService.updatePassword(
                        _changePasswordUiState.value.password
                    )
                )
            }
        }
    }

    fun clearChangePasswordResult() {
        _changePasswordUiState.update {
            it.copy(changePasswordResult = null)
        }
    }

    fun clearReAuthenticateInfo() {
        _changePasswordUiState.update {
            it.copy(
                isPasswordValid = true,
                isPasswordShow = false,
                password = "",
                reAuthenticateResult = null,
                changePasswordResult = null,
            )
        }
    }
}