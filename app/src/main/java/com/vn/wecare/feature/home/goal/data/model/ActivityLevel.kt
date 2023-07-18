package com.vn.wecare.feature.home.goal.data.model

import com.vn.wecare.utils.WecareUserConstantValues.ACTIVE_LEVEL
import com.vn.wecare.utils.WecareUserConstantValues.LIGHTLY_ACTIVE_LEVEL
import com.vn.wecare.utils.WecareUserConstantValues.MODERATELY_ACTIVE_LEVEL
import com.vn.wecare.utils.WecareUserConstantValues.SEDENTARY_LEVEL

enum class ActivityLevel(val value: String, val description: String, val caloriesIndex: Float) {
    SEDENTARY(SEDENTARY_LEVEL, "Little or no exercise", 1.2f), LIGHTLYACTIVE(
        LIGHTLY_ACTIVE_LEVEL, "Exercise 1-3 days a week", 1.375f
    ),
    MODERATELYACTIVE(MODERATELY_ACTIVE_LEVEL, "Exercise 3-5 days a week", 1.55f), ACTIVE(
        ACTIVE_LEVEL, "Exercise 6-7 days a week", 1.725f
    );

    companion object {
        fun getActivityLevelFromValue(value: String): ActivityLevel {
            return when (value) {
                SEDENTARY_LEVEL -> SEDENTARY
                LIGHTLY_ACTIVE_LEVEL -> LIGHTLYACTIVE
                MODERATELY_ACTIVE_LEVEL -> MODERATELYACTIVE
                else -> ACTIVE
            }
        }
    }
}