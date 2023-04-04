package com.vn.wecare.feature.account.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wecare_user")
data class WecareUser(
    @PrimaryKey val userId: String = "",
    val userName: String = "",
    val email: String = "",
    val emailVerified: Boolean = false,
    val gender: Boolean? = null,
    val age: Int? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val goal: String? = null
)