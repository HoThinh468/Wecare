package com.vn.wecare.feature.authentication.ui.login

import android.util.Log
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
        viewModelScope.launch {
            accountService.authenticate(email, password).collect {
                if (it == AuthenticationResult.SUCCESS) {
                    moveToHomeScreen()
                    clearLogInInformation()
                } else {
                    // Todo Show a dialog to notify users
                    Log.d("LogIn res: ", "fail")
                }
            }
        }
    }

    fun onShowPasswordClick() {
        loginUiState.value = loginUiState.value.copy(
            isPasswordShow = !loginUiState.value.isPasswordShow
        )
    }

    private fun clearLogInInformation() {
        loginUiState.value =
            loginUiState.value.copy(email = "", password = "", isPasswordShow = false)
    }
}