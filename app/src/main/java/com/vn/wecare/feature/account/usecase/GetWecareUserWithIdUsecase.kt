package com.vn.wecare.feature.account.usecase

import com.vn.wecare.feature.account.data.UserRepository
import com.vn.wecare.feature.account.data.model.WecareUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWecareUserWithIdUsecase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun getWecareUserWithId(userId: String): Flow<WecareUser?> {
        return userRepository.getLocalUserWithId(userId)
    }

    fun getFirebaseUserWithId(userId: String): Flow<WecareUser?> {
        return userRepository.getFirebaseUserWithId(userId)
    }
}