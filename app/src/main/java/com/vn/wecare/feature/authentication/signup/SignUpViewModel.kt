package com.vn.wecare.feature.authentication.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.feature.account.usecase.SaveUserToDbUsecase
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.utils.isValidEmail
import com.vn.wecare.utils.isValidPassword
import com.vn.wecare.utils.isValidUsername
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpUiState(
    val email: String = "",
    val userName: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isUserNameValid: Boolean = true,
    val snackbarMessageRes: Int? = null,
    val signUpResponse: Response<Boolean>? = null,
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val saveUserToDbUsecase: SaveUserToDbUsecase,
) : ViewModel() {

    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState = _signUpUiState.asStateFlow()

    fun onEmailChange(newVal: String) {
        _signUpUiState.update { it.copy(email = newVal) }
    }

    fun onPasswordChange(newVal: String) {
        _signUpUiState.update { it.copy(password = newVal) }
    }

    fun onUserNameChange(newVal: String) {
        _signUpUiState.update { it.copy(userName = newVal) }
    }

    fun onSignUpClick() {
        checkEmailValidation()
        checkUsernameValidation()
        checkPasswordValidation()
        viewModelScope.launch {
            if (_signUpUiState.value.isEmailValid && _signUpUiState.value.isPasswordValid && _signUpUiState.value.isUserNameValid) {
                _signUpUiState.update { it.copy(signUpResponse = Response.Loading) }
                _signUpUiState.update {
                    it.copy(
                        signUpResponse = accountService.createAccount(
                            it.email, it.password
                        )
                    )
                }
            } else {
                showSnackBarMessage(R.string.sign_up_error_message)
            }
        }
    }

    var isPasswordShow by mutableStateOf(false)
    fun onShowPasswordClick() {
        isPasswordShow = !isPasswordShow
    }

    fun snackbarMessageShown() {
        _signUpUiState.update { it.copy(snackbarMessageRes = null) }
    }

    private fun showSnackBarMessage(messageRes: Int) {
        _signUpUiState.update { it.copy(snackbarMessageRes = messageRes) }
    }

    fun handleSignUpSuccess(moveToOnboardingScreen: () -> Unit) = viewModelScope.launch {
        moveToOnboardingScreen()
        saveUserInformationToFirestoreDb()
        saveUserInformationToLocalDb()
        clearSignUpInformation()
    }

    fun handleSignUpError() {
        showSnackBarMessage(R.string.sign_up_error_message)
    }

    private fun checkEmailValidation() {
        if (_signUpUiState.value.email.isValidEmail()) _signUpUiState.update { it.copy(isEmailValid = true) }
        else _signUpUiState.update { it.copy(isEmailValid = false) }
    }

    fun getEmailErrorMessage(): Int? = if (_signUpUiState.value.isEmailValid) null
    else R.string.email_error_message

    private fun checkUsernameValidation() {
        if (_signUpUiState.value.userName.isValidUsername()) _signUpUiState.update {
            it.copy(isUserNameValid = true)
        }
        else _signUpUiState.update { it.copy(isUserNameValid = false) }
    }

    fun getUsernameErrorMessage(): Int? = if (_signUpUiState.value.isUserNameValid) null
    else R.string.username_error_message

    private fun checkPasswordValidation() {
        _signUpUiState.update { it.copy(isPasswordValid = _signUpUiState.value.password.isValidPassword()) }
    }

    fun getPasswordErrorMessage(): Int? = if (_signUpUiState.value.isPasswordValid) null
    else R.string.password_error_message

    private suspend fun saveUserInformationToFirestoreDb() {
        saveUserToDbUsecase.saveUserToFirestoreDb(
            accountService.currentUserId,
            _signUpUiState.value.email,
            _signUpUiState.value.userName,
            accountService.isUserEmailVerified
        )
    }

    private suspend fun saveUserInformationToLocalDb() {
        val user = WecareUser(
            userId = accountService.currentUserId,
            email = _signUpUiState.value.email,
            userName = _signUpUiState.value.userName,
            emailVerified = accountService.isUserEmailVerified
        )
        saveUserToDbUsecase.saveUserToLocalDb(user)
    }

    private fun clearSignUpInformation() {
        _signUpUiState.value = signUpUiState.value.copy(
            email = "", userName = "", password = "", signUpResponse = null
        )
    }
}