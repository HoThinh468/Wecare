package com.vn.wecare.feature.authentication.ui.service

enum class AuthenticationResult() {
    SUCCESS,
    ERROR
}

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean

    suspend fun createAccount(email: String, password: String) : AuthenticationResult
    suspend fun authenticate(email: String, password: String) : AuthenticationResult
    suspend fun sendVerificationEmail(email: String)
    suspend fun sendRecoveryEmail(email: String) : AuthenticationResult
    suspend fun signOut()
}