package com.vn.wecare.feature.account.data.datasource

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.dao.UserDao
import com.vn.wecare.feature.account.data.model.WecareUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalWecareUserDataSource @Inject constructor(
    private val wecareUserDao: UserDao,
) : WecareUserDataSource {

    override suspend fun insertUser(input: WecareUser) {
        wecareUserDao.insertNewUser(input)
    }

    override suspend fun deleteUser(input: WecareUser) {
        wecareUserDao.deleteUser(input)
    }

    override suspend fun getUserWithId(userId: String): Flow<Response<WecareUser?>> = flow {
        emit(
            try {
                val user = wecareUserDao.getUserWithId(userId)
                if (user != null) (Response.Success(user))
                else (Response.Error(Exception("User not found")))
            } catch (exception: Exception) {
                Response.Error(exception)
            }
        )
    }
}