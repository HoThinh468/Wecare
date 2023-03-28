package com.vn.wecare.feature.authentication.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ForgotPasswordUiState(
    val email: String = "",
    val snackbarMessageRes: Int? = null,
    val isEmailValid: Boolean = true,
    val sendRecoveryEmailResponse: Response<Boolean>? = null
)

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(newVal: String) {
        _uiState.update {
            it.copy(email = newVal)
        }
        checkEmailValidation()
    }

    fun snackbarMessageShown() {
        _uiState.update {
            it.copy(snackbarMessageRes = null)
        }
    }

    private fun showSnackBarMessage(messageRes: Int) {
        _uiState.update {
            it.copy(snackbarMessageRes = messageRes)
        }
    }

    private fun checkEmailValidation() {
        if (_uiState.value.email.isValidEmail()) _uiState.update { it.copy(isEmailValid = true) }
        else _uiState.update { it.copy(isEmailValid = false) }
    }

    fun onSendRecoveryEmailClick() {
        checkEmailValidation()
        viewModelScope.launch {
            if (_uiState.value.isEmailValid) {
                _uiState.update { it.copy(sendRecoveryEmailResponse = Response.Loading) }
                _uiState.update {
                    it.copy(sendRecoveryEmailResponse = accountService.sendRecoveryEmail(it.email))
                }
            } else showSnackBarMessage(R.string.email_error_message)
        }
    }

    fun handleSendRecoverySuccess(moveToSendSuccessScreen: () -> Unit) {
        moveToSendSuccessScreen()
        clearInformation()
    }

    fun handleSendRecoveryError() {
        showSnackBarMessage(R.string.send_recovery_email_error_message)
    }

    private fun clearInformation() {
        _uiState.update {
            it.copy(
                email = "",
                sendRecoveryEmailResponse = null
            )
        }
    }
}