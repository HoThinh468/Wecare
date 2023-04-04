package com.vn.wecare.feature.account.data.datasource

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.model.WecareUser
import kotlinx.coroutines.flow.Flow

interface WecareUserDataSource {

    suspend fun insertUser(input: WecareUser)

    suspend fun deleteUser(input: WecareUser)

    suspend fun getUserWithId(userId: String): Flow<Response<WecareUser?>>

    suspend fun updateUser(userId: String, field: String, value: Any): Flow<Response<Boolean>>
}