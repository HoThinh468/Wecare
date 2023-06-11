package com.vn.wecare.feature.home.step_count

fun Float.getCaloriesBurnedFromStepCount(): Int = (this * 0.05).toInt()

fun Float.getMoveTimeFromStepCount(): Int = (this * 0.008).toInt()