package com.vn.wecare.core

import com.vn.wecare.feature.account.data.model.WecareUser

object WecareUserSingleton {
    private var instance: WecareUser? = null

    fun getInstance(): WecareUser {
        if (instance == null) {
            instance = WecareUser()
        }
        return instance!!
    }
}
