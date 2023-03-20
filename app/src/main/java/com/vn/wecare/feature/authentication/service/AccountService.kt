package com.vn.wecare.feature.authentication.service

import com.vn.wecare.core.data.Response

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val isUserEmailVerified: Boolean

    suspend fun createAccount(email: String, password: String): Response<Boolean>
    suspend fun authenticate(email: String, password: String): Response<Boolean>
    suspend fun sendVerificationEmail(): Response<Boolean>
    suspend fun sendRecoveryEmail(email: String): Response<Boolean>
    suspend fun signOut(): Response<Boolean>
    suspend fun signInWithGoogle(idToken: String): Response<Boolean>
    suspend fun signInWithFacebook(token: String): Response<Boolean>
    suspend fun updatePassword(newPassword: String): Response<Boolean>
}