package com.vn.wecare.feature.authentication.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vn.wecare.R
import com.vn.wecare.core.snackbar.SnackbarManager
import com.vn.wecare.feature.authentication.ui.service.AccountService
import com.vn.wecare.feature.authentication.ui.service.AuthenticationResult
import com.vn.wecare.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class LoginUiState(
    val email: String = "", val password: String = "", val isPasswordShow: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    var loginUiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = loginUiState.value.email
    private val password
        get() = loginUiState.value.password

    fun onEmailChange(newVal: String) {
        loginUiState.value = loginUiState.value.copy(email = newVal)
    }

    fun onPasswordChange(newVal: String) {
        loginUiState.value = loginUiState.value.copy(password = newVal)
    }

    fun onSignInClick(moveToHomeScreen: () -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }
        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_password_error)
            return
        }
        if (accountService.authenticate(
                email, password
            ) == AuthenticationResult.SUCCESS
        ) {
            moveToHomeScreen()
        }
    }

    fun onShowPasswordClick() {
        loginUiState.value = loginUiState.value.copy(
            isPasswordShow = !loginUiState.value.isPasswordShow
        )
    }
}