package com.vn.wecare.feature.home.bmi.util

import com.vn.wecare.feature.onboarding.model.BMIState
import com.vn.wecare.utils.WecareUserConstantValues
import kotlin.math.pow

fun getBMIWithHeightAndWeight(height: Float, weight: Float): Float {
    return (weight / height.pow(2))
}

fun getWeightWithBMIAndHeight(bmi: Float, height: Float): Float {
    return (bmi * height.pow(2))
}

fun getBMIState(bmiIndex: Float): BMIState {
    return when (bmiIndex) {
        in WecareUserConstantValues.BMI_UNDERWEIGHT_RANGE -> BMIState.UNDERWEIGHT
        in WecareUserConstantValues.BMI_NORMAL_RANGE -> BMIState.NORMAL
        in WecareUserConstantValues.BMI_OVERWEIGHT_RANGE -> BMIState.OVERWEIGHT
        in WecareUserConstantValues.BMI_FAT_RANGE -> BMIState.FAT
        else -> BMIState.OBESITY
    }
}