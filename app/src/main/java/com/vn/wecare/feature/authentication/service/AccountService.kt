package com.vn.wecare.feature.authentication.service

import android.net.Uri
import com.vn.wecare.core.data.Response
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val isUserEmailVerified: Boolean
    val userAvatar: Uri?

    suspend fun createAccount(email: String, password: String): Response<Boolean>
    suspend fun authenticate(email: String, password: String): Response<Boolean>
    suspend fun sendVerificationEmail(): Flow<Response<Boolean>>
    suspend fun sendRecoveryEmail(email: String): Response<Boolean>
    suspend fun signOut(): Response<Boolean>
    suspend fun signInWithGoogle(idToken: String): Response<Boolean>
    suspend fun signInWithFacebook(token: String): Response<Boolean>
    suspend fun updatePassword(newPassword: String): Response<Boolean>
    suspend fun updateAvatar(newUri: Uri): Response<Boolean>
}