package com.vn.wecare.feature.account.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wecare_user")
data class WecareUser(
    @PrimaryKey val userId: String = "",
    val userName: String = "",
    val email: String = "",
    val isEmailVerified: Boolean = false
)