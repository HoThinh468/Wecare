package com.vn.wecare.feature.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vn.wecare.feature.authentication.ui.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    fun onSignOutClick(moveToAuthenticationGraph: () -> Unit) {
        viewModelScope.launch {
            accountService.signOut()
            moveToAuthenticationGraph()
        }
    }
}