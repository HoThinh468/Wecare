package com.vn.wecare.feature.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.alarm.ExactAlarms
import com.vn.wecare.core.alarm.InExactAlarms
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.usecase.ClearSharedPreferencesUsecase
import com.vn.wecare.feature.account.usecase.DeleteWecareUserUsecase
import com.vn.wecare.feature.account.usecase.GetWecareUserWithIdUsecase
import com.vn.wecare.feature.authentication.service.AccountService
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

    private fun updateAccountScreen() = viewModelScope.launch {
        val user = getWecareUserWithIdUsecase.getWecareUserWithId(accountService.currentUserId)
    }

    fun onSignOutClick(moveToAuthenticationGraph: () -> Unit) {
        viewModelScope.launch {
            moveToAuthenticationGraph()
            clearSharedPreferencesUsecase.clearAllSharedPref()
            stepCountExactAlarms.clearExactAlarm()
            stepCountInExactAlarms.clearInExactAlarm()
            getWecareUserWithIdUsecase.getWecareUserWithId(accountService.currentUserId).collect {
                if (it is Response.Success && it.data != null) {
                    deleteWecareUserUsecase.deleteAccount(
                        it.data.userId, it.data.userName, it.data.email
                    )
                }
            }
            accountService.signOut()
        }
    }
}