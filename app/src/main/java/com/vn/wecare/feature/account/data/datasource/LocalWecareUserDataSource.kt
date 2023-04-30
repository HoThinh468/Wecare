package com.vn.wecare.feature.account.data.datasource

import com.vn.wecare.core.data.Response
import com.vn.wecare.core.di.IoDispatcher
import com.vn.wecare.feature.account.data.dao.UserDao
import com.vn.wecare.feature.account.data.model.WecareUser
import com.vn.wecare.utils.WecareUserConstantValues.AGE_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.GENDER_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.GOAL_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.HEIGHT_FIELD
import com.vn.wecare.utils.WecareUserConstantValues.WEIGHT_FIELD
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class LocalWecareUserDataSource @Inject constructor(
    private val wecareUserDao: UserDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
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
    }.flowOn(ioDispatcher)

    override suspend fun updateUser(
        userId: String, field: String, value: Any
    ): Flow<Response<Boolean>> = flow {
        emit(
            try {
                when (field) {
                    GENDER_FIELD -> wecareUserDao.updateGenderWithUserId(
                        userId, value as Boolean
                    )

                    AGE_FIELD -> wecareUserDao.updateAgeWithUserId(userId, value as Int)
                    HEIGHT_FIELD -> wecareUserDao.updateHeightWithUserId(userId, value as Int)
                    WEIGHT_FIELD -> wecareUserDao.updateWeightWithUserId(userId, value as Int)
                    GOAL_FIELD -> wecareUserDao.updateGoalWithUserId(userId, value as String)
                    else -> { /* do nothing */
                    }
                }
                Response.Success(true)
            } catch (e: Exception) {
                Response.Error(e)
            }
        )
    }.flowOn(ioDispatcher)
}