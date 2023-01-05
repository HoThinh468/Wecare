package com.vn.wecare.feature.authentication.ui.service

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override suspend fun createAccount(
        email: String, password: String
    ): Flow<AuthenticationResult> = flow {
        var result = AuthenticationResult.ERROR
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    result = AuthenticationResult.SUCCESS
                }
            }.await()
        } catch (exception: FirebaseAuthException) {
            Log.d("Create account exception: ", exception.message.toString())
        }
        emit(result)
    }

    override suspend fun authenticate(email: String, password: String): Flow<AuthenticationResult> =
        flow {
            var result = AuthenticationResult.ERROR
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        result = AuthenticationResult.SUCCESS
                    }
                }.await()
            } catch (exception: FirebaseAuthException) {
                Log.d("Login exception: ", exception.message.toString())
            }
            emit(result)
        }

    override suspend fun sendVerificationEmail(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun sendRecoveryEmail(email: String) : Flow<AuthenticationResult> = flow {
        var result = AuthenticationResult.ERROR
        try {
            auth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    result = AuthenticationResult.SUCCESS
                }
            }.await()
        } catch (exception: FirebaseAuthException) {
            Log.d("Send password reset email exception: ", exception.message.toString())
        }
        emit(result)
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}