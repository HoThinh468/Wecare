package com.vn.wecare.feature.authentication.ui.forgotpassword

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.R
import com.vn.wecare.core.snackbar.SnackbarManager
import com.vn.wecare.feature.authentication.ui.service.AccountService
import com.vn.wecare.feature.authentication.ui.service.AuthenticationResult
import com.vn.wecare.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ForgotPasswordUiState(
    val email: String = ""
)

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    var uiState = mutableStateOf(ForgotPasswordUiState())
        private set

    private val email
        get() = uiState.value.email

    fun onEmailChange(newVal: String) {
        uiState.value = uiState.value.copy(email = newVal)
    }

    fun onSendRecoveryEmailClick(moveToSendSuccessScreen: () -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }
        viewModelScope.launch {
            if (accountService.sendRecoveryEmail(email) == AuthenticationResult.SUCCESS) {
                clearInformation()
                moveToSendSuccessScreen()
            }
        }
    }

    private fun clearInformation() {
        uiState.value = uiState.value.copy(email = "")
    }
}