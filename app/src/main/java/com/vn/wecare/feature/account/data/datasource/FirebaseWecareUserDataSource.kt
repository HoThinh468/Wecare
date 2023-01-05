package com.vn.wecare.feature.account.data.datasource

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vn.wecare.feature.account.data.model.WecareUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val WECARE_USER_COLLECTION_PATH = "wecareUser"

class FirebaseWecareUserDataSource @Inject constructor(
    private val db: FirebaseFirestore
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

    override fun getUserWithId(userId: String): Flow<WecareUser?> = flow {
        var user = WecareUser()
        db.collection(WECARE_USER_COLLECTION_PATH).document(userId).get().addOnSuccessListener {
            if (it != null) {
                user = it.toObject(WecareUser::class.java) ?: WecareUser()
            }
        }.await()
        Log.d("Get user from firebase res: ", user.toString())
        emit(user)
    }
}