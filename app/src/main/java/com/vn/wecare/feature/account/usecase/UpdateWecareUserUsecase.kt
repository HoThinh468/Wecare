package com.vn.wecare.feature.account.usecase

import com.vn.wecare.core.data.Response
import com.vn.wecare.feature.account.data.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateWecareUserUsecase @Inject constructor(private val userRepository: UserRepository) {
    suspend fun updateWecareUserFirestoreDbWithId(userId: String, field: String, value: Any) =
        userRepository.updateUserInformationInFirestoreWithId(userId, field, value)

    suspend fun updateWecareUserRoomDbWithId(userId: String, field: String, value: Any) =
        userRepository.updateUserInformationInRoomWithId(userId, field, value)
}