package com.vn.wecare.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.usecase.GetWecareUserWithIdUsecase
import com.vn.wecare.feature.authentication.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isUserNull: Boolean = false, val isAdditionInfoMissing: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWecareUserWithIdUsecase: GetWecareUserWithIdUsecase,
    private val accountService: AccountService
) : ViewModel() {

    private val _mainActivityUiState = MutableStateFlow(HomeUiState())
    val mainActivityUiState = _mainActivityUiState.asStateFlow()

    fun checkIfAdditionalInformationMissing() {
        viewModelScope.launch {
            getWecareUserWithIdUsecase.getUserFromRoomWithId(accountService.currentUserId)
                .collect { res ->
                    if (res is Response.Success) {
                        if (res.data?.gender == null || res.data.age == null || res.data.height == null || res.data.weight == null || res.data.goal == null) {
                            _mainActivityUiState.update { it.copy(isAdditionInfoMissing = true) }
                        }
                    }
                }
        }
    }

    fun checkIfUserIsNull() {
        _mainActivityUiState.update {
            it.copy(isUserNull = !accountService.hasUser)
        }
    }
}