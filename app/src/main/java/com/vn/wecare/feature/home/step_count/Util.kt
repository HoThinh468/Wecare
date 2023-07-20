package com.vn.wecare.feature.home.step_count

import com.vn.wecare.core.WecareUserSingletonObject

fun Float.getCaloriesBurnedFromStepCount(height: Int, weight: Int): Int {
    val stride = StepsUtil.getStrideByUserHeight(height)
    val distance = StepsUtil.getDistanceBySteps(this.toInt(), stride)
    val time = StepsUtil.getAverageMoveTimeByDistance(distance) // Average pace
    // 3.5 is the average MET
    return (time * 3.5 * 3.5 * (weight.toFloat() / 12000)).toInt()
}

fun Float.getMoveTimeFromStepCount(height: Int): Int {
    val stride = StepsUtil.getStrideByUserHeight(height)
    val distance = StepsUtil.getDistanceBySteps(this.toInt(), stride)
    // Unit return: seconds
    return StepsUtil.getAverageMoveTimeByDistance(distance).toInt()
}
fun Int.getCaloriesBurnedFromStepCount(height: Int, weight: Int): Int {
    val stride = StepsUtil.getStrideByUserHeight(height)
    val distance = StepsUtil.getDistanceBySteps(this, stride)
    val time = StepsUtil.getAverageMoveTimeByDistance(distance) // Average pace
    // 3.5 is the average MET
    return (time * 3.5 * 3.5 * (weight.toFloat() / 12000)).toInt()
}

fun Int.getMoveTimeFromStepCount(height: Int): Int {
    val stride = StepsUtil.getStrideByUserHeight(height/100)
    val distance = StepsUtil.getDistanceBySteps(this, stride)
    // Unit return: seconds
    return StepsUtil.getAverageMoveTimeByDistance(distance).toInt()
}

fun Int.getStepsFromCaloriesBurned(height: Int, weight: Int): Int {
    val stride = StepsUtil.getStrideByUserHeight(height) // 0.7
    return ((this * 1.34 * 12000) / (stride * 3.5 * 3.5 * weight)).toInt()
}