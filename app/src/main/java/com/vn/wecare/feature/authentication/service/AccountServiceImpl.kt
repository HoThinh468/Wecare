package com.vn.wecare.feature.authentication.service

import android.net.Uri
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.vn.wecare.core.data.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val isUserEmailVerified: Boolean
        get() = auth.currentUser?.isEmailVerified ?: false

    override val userAvatar: Uri?
        get() = auth.currentUser?.photoUrl

    override suspend fun createAccount(
        email: String, password: String
    ): Response<Boolean> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Response.Success(true)
        } catch (exception: FirebaseAuthException) {
            Response.Error(exception)
        }
    }

    override suspend fun authenticate(
        email: String, password: String
    ): Response<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Response.Success(true)
        } catch (exception: FirebaseAuthException) {
            Response.Error(exception)
        }
    }

    override suspend fun sendVerificationEmail(): Flow<Response<Boolean>> = flow {
        emit(
            try {
                auth.currentUser?.sendEmailVerification()?.await()
                Response.Success(true)
            } catch (exception: Exception) {
                Response.Error(exception)
            }
        )
    }

    override suspend fun sendRecoveryEmail(email: String): Response<Boolean> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Response.Success(true)
        } catch (exception: FirebaseAuthException) {
            Response.Error(exception)
        }
    }

    override suspend fun signOut(): Response<Boolean> {
        return try {
            auth.signOut()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Response<Boolean> {
        return try {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(firebaseCredential).await()
            Response.Success(true)
        } catch (exception: Exception) {
            Response.Error(exception)
        }
    }

    override suspend fun signInWithFacebook(token: String): Response<Boolean> {
        return try {
            val firebaseCredential = FacebookAuthProvider.getCredential(token)
            auth.signInWithCredential(firebaseCredential).await()
            Response.Success(true)
        } catch (exception: Exception) {
            Response.Error(exception)
        }
    }

    override suspend fun updatePassword(newPassword: String): Response<Boolean> {
        return try {
            auth.currentUser!!.updatePassword(newPassword).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    override suspend fun updateAvatar(newUri: Uri): Response<Boolean> {
        val newProfile = userProfileChangeRequest {
            photoUri = newUri
        }
        return try {
            auth.currentUser!!.updateProfile(newProfile).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }

    override suspend fun reAuthenticateUser(password: String): Response<Boolean> {
        val credential =
            EmailAuthProvider.getCredential(auth.currentUser?.email.toString(), password)

        return try {
            auth.currentUser?.reauthenticate(credential)?.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Error(e)
        }
    }
}