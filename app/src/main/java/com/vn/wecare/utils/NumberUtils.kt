package com.vn.wecare.utils

fun getProgressInFloatWithIntInput(currentIndex: Int, targetIndex: Int): Float {
    return if (targetIndex == 0) {
        0f
    } else currentIndex.toFloat() / targetIndex.toFloat()
}