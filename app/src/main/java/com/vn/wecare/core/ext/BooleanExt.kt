package com.vn.wecare.core.ext

fun Boolean.toGender(): String {
    return if (this) "Male" else "Female"
}