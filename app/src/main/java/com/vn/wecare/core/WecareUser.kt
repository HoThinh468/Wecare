package com.vn.wecare.core

import com.vn.wecare.feature.account.data.model.WecareUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object WecareUserSingleton {
    private val instance = MutableStateFlow(WecareUser())

    fun getInstance() = instance.value

    fun getInstanceFlow(): Flow<WecareUser> {
        return instance.asStateFlow()
    }

    fun updateInstance(user: WecareUser) {
        instance.value = user
    }
}
