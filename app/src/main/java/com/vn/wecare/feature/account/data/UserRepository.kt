package com.vn.wecare.feature.account.data

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
    suspend fun insertUser(user: WecareUser) {
        coroutineScope {
            launch { firebaseWecareUserDataSource.insertUser(user) }
            launch { insertUserToLocaldb(user) }
        }
    }

    suspend fun insertUserToLocaldb(user: WecareUser) {
        localWecareUserDataSource.insertUser(user)
    }

    suspend fun deleteUser(user: WecareUser) {
        coroutineScope {
            launch { localWecareUserDataSource.deleteUser(user) }
        }
    }

    fun getLocalUserWithId(userId: String): Flow<WecareUser?> {
        return localWecareUserDataSource.getUserWithId(userId)
    }

    fun getFirebaseUserWithId(userId: String): Flow<WecareUser?> {
        return firebaseWecareUserDataSource.getUserWithId(userId)
    }
}