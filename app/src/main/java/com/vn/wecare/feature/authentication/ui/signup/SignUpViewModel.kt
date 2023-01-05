package com.vn.wecare.feature.authentication.ui.signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.R
import com.vn.wecare.core.snackbar.SnackbarManager
import com.vn.wecare.feature.account.usecase.CreateNewWecareUserUsecase
import com.vn.wecare.feature.authentication.ui.service.AccountService
import com.vn.wecare.feature.authentication.ui.service.AuthenticationResult
import com.vn.wecare.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpUiState(
    val email: String = "",
    val userName: String = "",
    val password: String = "",
    val isPasswordShow: Boolean = false
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val createNewWecareUserUsecase: CreateNewWecareUserUsecase
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

    fun onSignUpClick(moveToHomeScreen: () -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }
        if (userName.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_username_error)
            return
        }
        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.empty_password_error)
            return
        }
        viewModelScope.launch {
            accountService.createAccount(email, password).collect {
                if (it == AuthenticationResult.SUCCESS) {
                    createNewWecareUserUsecase.createNewWecareUser(
                        accountService.currentUserId, email, userName
                    )
                    moveToHomeScreen()
                    clearSignUpInformation()
                } else {
                    // Todo Show a dialog to notify users
                    Log.d("Signup res: ", "fail")
                }
            }
        }
    }

    fun onShowPasswordClick() {
        signUpUiState.value = signUpUiState.value.copy(
            isPasswordShow = !signUpUiState.value.isPasswordShow
        )
    }

    fun clearSignUpInformation() {
        signUpUiState.value = signUpUiState.value.copy(
            email = "", userName = "", password = "", isPasswordShow = false
        )
    }
}