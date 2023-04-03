package com.vn.wecare.feature.account.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.UserRepository
import com.vn.wecare.feature.account.data.model.WecareUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWecareUserWithIdUsecase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun getUserFromRoomWithId(userId: String): Flow<Response<WecareUser?>> {
        return userRepository.getLocalUserWithId(userId)
    }

    suspend fun getUserFromFirebaseWithId(userId: String): Flow<Response<WecareUser?>> {
        return userRepository.getFirebaseUserWithId(userId)
    }
}