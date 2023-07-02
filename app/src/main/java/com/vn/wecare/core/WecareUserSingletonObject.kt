package com.vn.wecare.core

import com.vn.wecare.feature.account.data.model.WecareUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object WecareUserSingletonObject {
    private val instance = MutableStateFlow(WecareUser())

    fun getInstance() = instance.value

    fun getInstanceFlow(): Flow<WecareUser> {
        return instance.asStateFlow()
    }

    fun updateInstance(user: WecareUser?) {
        if (user == null) {
            instance.value = WecareUser(
                userId = "",
                userName = "",
                email = "",
                emailVerified = false,
                gender = null,
                age = null,
                height = null,
                weight = null,
                goal = null
            )
        } else {
            instance.value = user
        }
    }
}
