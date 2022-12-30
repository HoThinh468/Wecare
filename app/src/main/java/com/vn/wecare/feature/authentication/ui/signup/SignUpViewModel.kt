package com.vn.wecare.feature.authentication.ui.signup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vn.wecare.R
import com.vn.wecare.core.snackbar.SnackbarManager
import com.vn.wecare.feature.authentication.ui.service.AccountService
import com.vn.wecare.feature.authentication.ui.service.AuthenticationResult
import com.vn.wecare.utils.isValidEmail
import com.vn.wecare.utils.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class SignUpUiState(
    val email: String = "",
    val userName: String = "",
    val password: String = "",
    val isPasswordShow: Boolean = false
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    var signUpUiState = mutableStateOf(SignUpUiState())
        private set

    private val email
        get() = signUpUiState.value.email
    private val userName
        get() = signUpUiState.value.userName
    private val password
        get() = signUpUiState.value.password

    fun onEmailChange(newVal: String) {
        signUpUiState.value = signUpUiState.value.copy(email = newVal)
    }

    fun onPasswordChange(newVal: String) {
        signUpUiState.value = signUpUiState.value.copy(password = newVal)
    }

    fun onUserNameChange(newVal: String) {
        signUpUiState.value = signUpUiState.value.copy(userName = newVal)
    }

    fun isEmailValid(): Boolean {
        return email.isValidEmail()
    }

    fun isPasswordValid(): Boolean {
        return password.isValidPassword()
    }

    fun onSignUpClick(moveToHomeScreen: () -> Unit) {
        if (!isEmailValid()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }
        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_password_error)
            return
        }
        if (accountService.createAccount(
                email, password
            ) == AuthenticationResult.SUCCESS
        ) moveToHomeScreen()
    }

    fun onShowPasswordClick() {
        signUpUiState.value = signUpUiState.value.copy(
            isPasswordShow = !signUpUiState.value.isPasswordShow
        )
    }
}