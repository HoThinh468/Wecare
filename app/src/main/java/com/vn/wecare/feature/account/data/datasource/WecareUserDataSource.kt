package com.vn.wecare.feature.account.data.datasource

import com.vn.wecare.feature.account.data.model.WecareUser
import kotlinx.coroutines.flow.Flow

interface WecareUserDataSource {

    suspend fun insertUser(input: WecareUser)

    suspend fun deleteUser(input: WecareUser)

    fun getUserWithId(userId: String): Flow<WecareUser?>
}