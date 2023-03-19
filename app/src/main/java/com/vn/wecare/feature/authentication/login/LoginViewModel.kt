package com.vn.wecare.feature.authentication.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.R
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.usecase.GetWecareUserWithIdUsecase
import com.vn.wecare.feature.account.usecase.SaveUserToLocalDbUsecase
import com.vn.wecare.feature.authentication.service.AccountService
import com.vn.wecare.feature.home.step_count.usecase.GetCurrentStepsFromSensorUsecase
import com.vn.wecare.feature.home.step_count.usecase.UpdatePreviousTotalSensorSteps
import com.vn.wecare.utils.isValidEmail
import com.vn.wecare.utils.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LogInUiState(
    val authenticationResponse: Response<Boolean>? = null,
    val snackbarMessageRes: Int? = null,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    private val getWecareUserWithIdUsecase: GetWecareUserWithIdUsecase,
    private val saveUserToLocalDbUsecase: SaveUserToLocalDbUsecase,
    private val getCurrentStepsFromSensorUsecase: GetCurrentStepsFromSensorUsecase,
    private val updatePreviousTotalSensorSteps: UpdatePreviousTotalSensorSteps
) : ViewModel() {

    private val _logInUiState = MutableStateFlow(LogInUiState())
    val logInUiState: StateFlow<LogInUiState> = _logInUiState.asStateFlow()

    var inputEmail by mutableStateOf("")
    fun onEmailChange(newVal: String) {
        checkEmailValidation()
        inputEmail = newVal
    }

    var inputPassword by mutableStateOf("")
    fun onPasswordChange(newVal: String) {
        checkPasswordValidation()
        inputPassword = newVal
    }

    fun onSignInClick() = viewModelScope.launch {
        checkEmailValidation()
        checkPasswordValidation()
        if (_logInUiState.value.isEmailValid && _logInUiState.value.isPasswordValid) {
            _logInUiState.update { it.copy(authenticationResponse = Response.Loading) }
            _logInUiState.update {
                it.copy(
                    authenticationResponse = accountService.authenticate(
                        inputEmail, inputPassword
                    )
                )
            }
        } else {
            showSnackBarMessage(R.string.log_in_error_message)
        }
    }

    fun onSignInWithGoogleClick(idToken: String) = viewModelScope.launch {
        _logInUiState.update { it.copy(authenticationResponse = Response.Loading) }
        _logInUiState.update {
            it.copy(
                authenticationResponse = accountService.signInWithGoogle(idToken)
            )
        }
    }

    fun onSignInWithFacebookClick(idToken: String) = viewModelScope.launch {
        _logInUiState.update { it.copy(authenticationResponse = Response.Loading) }
        _logInUiState.update {
            it.copy(
                authenticationResponse = accountService.signInWithFacebook(idToken)
            )
        }
    }

    var isPasswordShow by mutableStateOf(false)
    fun onShowPasswordClick() {
        isPasswordShow = !isPasswordShow
    }

    fun snackbarMessageShown() {
        _logInUiState.update { it.copy(snackbarMessageRes = null) }
    }

    fun handleLoginSuccess(moveToHome:  () -> Unit) {
        moveToHome()
        clearLogInInformation()
        viewModelScope.launch {
            saveUserInformationToLocalDb()
        }
    }

    fun handleLoginError() {
        showSnackBarMessage(R.string.log_in_error_message)
        _logInUiState.update { it.copy(authenticationResponse = null) }
    }

    private fun checkEmailValidation() {
        if (inputEmail.isValidEmail()) _logInUiState.update { it.copy(isEmailValid = true) }
        else _logInUiState.update { it.copy(isEmailValid = false) }
    }

    fun getEmailErrorMessage(): Int? = if (_logInUiState.value.isEmailValid) null
    else R.string.email_error_message

    private fun checkPasswordValidation() {
        if (inputPassword.isValidPassword()) _logInUiState.update { it.copy(isPasswordValid = true) }
        else _logInUiState.update { it.copy(isPasswordValid = false) }
    }

    fun getPasswordErrorMessage(): Int? = if (_logInUiState.value.isPasswordValid) null
    else R.string.password_error_message

    private fun showSnackBarMessage(messageRes: Int) {
        _logInUiState.update { it.copy(snackbarMessageRes = messageRes) }
    }

    private suspend fun saveUserInformationToLocalDb() {
        val userFlow = getWecareUserWithIdUsecase.getUserFromFirebaseWithId(accountService.currentUserId)
        userFlow.collect {
            if (it is Response.Success) {
                it.data?.let { user ->
                    Log.d("New user login with id: ${user.userId}", "")
                    saveUserToLocalDbUsecase.saveNewUserToLocalDb(
                        it.data.userId, it.data.email, it.data.userName
                    )
                }
            }
        }
    }

    fun clearLogInInformation() {
        _logInUiState.update {
            it.copy(authenticationResponse = null)
        }
        inputEmail = ""
        inputPassword = ""
    }
}