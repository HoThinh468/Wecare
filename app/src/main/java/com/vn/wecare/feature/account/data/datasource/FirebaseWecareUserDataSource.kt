package com.vn.wecare.feature.account.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.account.data.model.WecareUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val WECARE_USER_COLLECTION_PATH = "wecareUser"

class FirebaseWecareUserDataSource @Inject constructor(
    private val db: FirebaseFirestore, @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : WecareUserDataSource {

    override suspend fun insertUser(input: WecareUser) {
        db.collection(WECARE_USER_COLLECTION_PATH).document(input.userId).set(input)
            .addOnSuccessListener {
                Log.d(
                    "Insert user firestore", "DocumentSnapshot successfully written!"
                )
            }.addOnFailureListener { e ->
                Log.w(
                    "Insert user firestore", "Error writing document", e
                )
            }
    }

    override suspend fun deleteUser(input: WecareUser) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserWithId(userId: String): Flow<Response<WecareUser?>> = flow {
        val res = db.collection(WECARE_USER_COLLECTION_PATH).document(userId).get().await()
        if (res.data != null) {
            emit(Response.Success(res.toObject(WecareUser::class.java)))
        } else emit(Response.Error(null))
    }.flowOn(ioDispatcher)

    override suspend fun updateUser(
        userId: String, field: String, value: Any
    ): Flow<Response<Boolean>> = flow {
        emit(
            try {
                db.collection(WECARE_USER_COLLECTION_PATH).document(userId).update(field, value)
                    .await()
                Response.Success(true)
            } catch (e: Exception) {
                Response.Error(e)
            }
        )
    }.flowOn(ioDispatcher)
}