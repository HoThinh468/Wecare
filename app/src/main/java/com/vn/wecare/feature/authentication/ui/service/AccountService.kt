package com.vn.wecare.feature.authentication.ui.service

import kotlinx.coroutines.flow.Flow

enum class AuthenticationResult {
    SUCCESS,
    ERROR
}

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    suspend fun createAccount(email: String, password: String) : Flow<AuthenticationResult>
    suspend fun authenticate(email: String, password: String) : Flow<AuthenticationResult>
    suspend fun sendVerificationEmail(email: String)
    suspend fun sendRecoveryEmail(email: String) : Flow<AuthenticationResult>
    suspend fun signOut()
}