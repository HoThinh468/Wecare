package com.vn.wecare.utils

fun getProgressInFloatWithIntInput(currentIndex: Int, targetIndex: Int): Float {
    return if (targetIndex == 0) {
        0f
    } else currentIndex.toFloat() / targetIndex.toFloat() * 100
}

fun getProgressInFloatWithFloatInput(currentIndex: Float, targetIndex: Float): Float {
    return if (targetIndex == 0f) {
        0f
    } else currentIndex / targetIndex * 100
}