package com.vn.wecare.feature.account.usecase

import com.vn.wecare.feature.account.data.UserRepository
import com.vn.wecare.feature.account.data.model.WecareUser
import javax.inject.Inject

class SaveUserToLocalDbUsecase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun saveNewUserToLocalDb(userId: String, email: String, userName: String, isEmailVerified: Boolean) {
        val newUser = WecareUser(
            userId, userName, email, isEmailVerified
        )
        userRepository.insertUserToLocaldb(newUser)
    }
}