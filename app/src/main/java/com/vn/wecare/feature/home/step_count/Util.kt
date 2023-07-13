package com.vn.wecare.feature.home.step_count

import com.vn.wecare.core.WecareUserSingletonObject

fun Float.getCaloriesBurnedFromStepCount(): Int = ((2.9 * (WecareUserSingletonObject.getInstance().weight ?: 60) * this) / 2000 ).toInt()

fun Float.getMoveTimeFromStepCount(): Int = (this / 100).toInt()
fun Int.getCaloriesBurnedFromStepCount(): Int = ((2.9 * (WecareUserSingletonObject.getInstance().weight?.toDouble() ?: 60.0) * this) / 2000.0 ).toInt()

fun Int.getMoveTimeFromStepCount(): Int = (this.toDouble() / 100.0).toInt()

fun Int.getStepsFromCaloriesBurned(): Int = ((this * 2000) / (2.9 * (WecareUserSingletonObject.getInstance().weight?.toDouble() ?: 60.0))).toInt()
fun Int.getMoveTimeFromSteps(): Int = this / 100