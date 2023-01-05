package com.vn.wecare.feature.account.data.datasource

import com.vn.wecare.feature.account.data.dao.UserDao
import com.vn.wecare.feature.account.data.model.WecareUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalWecareUserDataSource @Inject constructor(
    private val wecareUserDao: UserDao
) : WecareUserDataSource {

    override suspend fun insertUser(input: WecareUser) {
        wecareUserDao.insertNewUser(input)
    }

    override suspend fun deleteUser(input: WecareUser) {
        wecareUserDao.deleteUser(input)
    }

    override fun getUserWithId(userId: String): Flow<WecareUser?> {
        return wecareUserDao.getUserWithId(userId)
    }
}