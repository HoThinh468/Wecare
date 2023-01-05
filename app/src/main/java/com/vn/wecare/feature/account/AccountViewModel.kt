package com.vn.wecare.feature.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.feature.account.usecase.ClearSharedPreferencesUsecase
import com.vn.wecare.feature.account.usecase.DeleteWecareUserUsecase
import com.vn.wecare.feature.account.usecase.GetWecareUserWithIdUsecase
import com.vn.wecare.feature.authentication.ui.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AccountUiState(
    val username: String = "", val email: String = ""
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

    var accountUiState = mutableStateOf(AccountUiState())
        private set

    init {
        updateAccountScreen()
    }

    private fun updateAccountScreen() {
        viewModelScope.launch {
            getWecareUserWithIdUsecase.getWecareUserWithId(accountService.currentUserId).collect {
                if (it != null) {
                    accountUiState.value = accountUiState.value.copy(
                        username = it.userName, email = it.email
                    )
                }
            }
        }
    }

    fun onSignOutClick(moveToAuthenticationGraph: () -> Unit) {
        viewModelScope.launch {
            moveToAuthenticationGraph()
            clearSharedPreferencesUsecase.clearAllSharedPref()
            stepCountExactAlarms.clearExactAlarm()
            stepCountInExactAlarms.clearInExactAlarm()
            getWecareUserWithIdUsecase.getWecareUserWithId(accountService.currentUserId).collect {
                if (it != null) {
                    deleteWecareUserUsecase.deleteAccount(it.userId, it.userName, it.email)
                }
            }
            accountService.signOut()
        }
    }
}