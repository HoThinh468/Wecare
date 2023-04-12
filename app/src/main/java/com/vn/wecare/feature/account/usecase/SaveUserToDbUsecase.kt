package com.vn.wecare.feature.account.usecase

import com.vn.wecare.feature.account.data.UserRepository
import com.vn.wecare.feature.account.data.model.WecareUser
import javax.inject.Inject

class SaveUserToDbUsecase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun saveUserToLocalDb(newUser: WecareUser) {
        userRepository.insertUserToLocaldb(newUser)
    }

    suspend fun saveUserToFirestoreDb(
        userId: String, email: String, userName: String, isEmailVerified: Boolean
    ) {
        val newUser = WecareUser(
            userId, userName, email, isEmailVerified
        )
        userRepository.insertUserToFirebase(newUser)
    }
}