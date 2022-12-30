package com.vn.wecare.feature.authentication.ui.service

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override fun createAccount(email: String, password: String) : AuthenticationResult {
        var result = AuthenticationResult.SUCCESS
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                result = AuthenticationResult.ERROR
            }
        }
        return result
    }

    override fun authenticate(email: String, password: String) : AuthenticationResult {
        var result = AuthenticationResult.SUCCESS
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (!it.isSuccessful) result = AuthenticationResult.ERROR
        }
        return result
    }

    override suspend fun sendVerificationEmail(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}