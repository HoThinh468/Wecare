package com.vn.wecare.feature.account.data

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.datasource.WecareUserDataSource
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.feature.account.di.FirebaseUserDatasource
import com.vn.wecare.feature.account.di.LocalUserDatasource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepository @Inject constructor(
    @LocalUserDatasource private val localWecareUserDataSource: WecareUserDataSource,
    @FirebaseUserDatasource private val firebaseWecareUserDataSource: WecareUserDataSource
) {
    suspend fun insertUserToFirebase(user: WecareUser) {
        firebaseWecareUserDataSource.insertUser(user)
    }

    suspend fun insertUserToLocaldb(user: WecareUser) {
        localWecareUserDataSource.insertUser(user)
    }

    suspend fun deleteUser(user: WecareUser) {
        coroutineScope {
            launch { localWecareUserDataSource.deleteUser(user) }
        }
    }

    suspend fun getLocalUserWithId(userId: String): Flow<Response<WecareUser?>> {
        return localWecareUserDataSource.getUserWithId(userId)
    }

    suspend fun getFirebaseUserWithId(userId: String): Flow<Response<WecareUser?>> {
        return firebaseWecareUserDataSource.getUserWithId(userId)
    }

    suspend fun updateUserInformationInFirestoreWithId(
        userId: String, field: String, value: Any
    ): Flow<Response<Boolean>> {
        return firebaseWecareUserDataSource.updateUser(userId, field, value)
    }

    suspend fun updateUserInformationInRoomWithId(
        userId: String, field: String, value: Any
    ): Flow<Response<Boolean>> {
        return localWecareUserDataSource.updateUser(userId, field, value)
    }
}