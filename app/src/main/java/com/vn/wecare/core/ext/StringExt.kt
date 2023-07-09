package com.vn.wecare.core.ext

import java.lang.Math.round
import kotlin.math.roundToInt

fun String.toIntFromString(): Int {
    return try {
        val decimalNumber = this.toDouble()
        decimalNumber.roundToInt()
    } catch (e: Exception) {
        0
    }
}

fun convertBMIToString(height: Int, weight: Int): String {
    return try {
        val decimalHeight = height.toDouble() / 100
        val decimalWeight = weight.toDouble()
        val bmi = decimalWeight / (decimalHeight * decimalHeight)
        String.format("%.2f", bmi)
    } catch (e: Exception) {
        "0.00"
    }
}

fun String.fromStringToGender(): Boolean {
    return this == "Male"
}