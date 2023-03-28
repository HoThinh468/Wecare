package com.vn.wecare.feature.account

import android.net.Uri
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
    val userNameLogo: String = "",
    val avatarUri: Uri? = null
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
        _accountUiState.update {
            it.copy(avatarUri = accountService.userAvatar)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(AccountFlowTAG, "Account viewmodel on cleared")
    }

    fun updateAccountScreen() = viewModelScope.launch {
        val user = getWecareUserWithIdUsecase.getUserFromRoomWithId(accountService.currentUserId)
        user.collect { res ->
            if (res is Response.Success && res.data != null) {
                _accountUiState.update {
                    it.copy(
                        username = res.data.userName,
                        email = res.data.email,
                        isEmailVerified = res.data.isEmailVerified,
                        userNameLogo = res.data.userName[0].uppercase(),
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
                        it.data.userId, it.data.userName, it.data.email, it.data.isEmailVerified
                    )
                }
            }
        }
        clearAccountUIState()
    }

    fun handleSignOutError() {
        Log.d(AccountFlowTAG, "Sign out fail, cannot delete user from local db")
        _accountUiState.update {
            it.copy(signOutResponse = null)
        }
    }

    fun pickImageUriFromPhone(uri: Uri?) {
        _accountUiState.update {
            it.copy(avatarUri = uri)
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
                avatarUri = null
            )
        }
    }
}