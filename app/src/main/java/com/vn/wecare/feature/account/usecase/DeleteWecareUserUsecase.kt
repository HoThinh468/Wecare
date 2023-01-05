package com.vn.wecare.feature.account.usecase

import com.vn.wecare.feature.account.data.UserRepository
import com.vn.wecare.feature.account.data.model.WecareUser
import javax.inject.Inject

class DeleteWecareUserUsecase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun deleteAccount(userId: String, email: String, userName: String) {
        val deleteUser = WecareUser(
            userId, userName, email
        )
        userRepository.deleteUser(deleteUser)
    }
}