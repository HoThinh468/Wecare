package com.vn.wecare.feature.account.usecase

import com.vn.wecare.feature.account.data.UserRepository
import com.vn.wecare.feature.account.data.model.WecareUser
import javax.inject.Inject

class CreateNewWecareUserUsecase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend fun createNewWecareUser(userId: String, email: String, userName: String) {
        val newUser = WecareUser(
            userId, userName, email
        )
        userRepository.insertUser(newUser)
    }
}