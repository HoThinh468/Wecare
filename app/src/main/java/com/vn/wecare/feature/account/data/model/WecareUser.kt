package com.vn.wecare.feature.account.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wecare_user")
data class WecareUser(
    @PrimaryKey var userId: String = "",
    var userName: String = "",
    var email: String = "",
    var emailVerified: Boolean = false,
    var gender: Boolean? = null,
    var age: Int? = null,
    var height: Int? = null,
    var weight: Int? = null,
    var goal: String? = null
)