package com.vn.wecare.utils

object WecareUserConstantValues {

    /* Common */
    const val NUMBER_OF_DAYS_IN_WEEK = 7
    const val ONE_HUNDRED_PERCENT_VALUE = 100
    const val KCAL_TO_CAL = 10000
    const val EXPANDABLE_TIME_MILLIS = 300
    const val DAY_TO_MILLISECONDS = 86_400_000L
    const val WEEK_TO_MILLISECONDS = 604_800_000L

    /* Wecare user object field */
    const val EMAIL_VERIFIED_FIELD = "emailVerified"
    const val EMAIL_FIELD = "email"
    const val AGE_FIELD = "age"
    const val GENDER_FIELD = "gender"
    const val HEIGHT_FIELD = "height"
    const val WEIGHT_FIELD = "weight"
    const val GOAL_FIELD = "goal"

    /* Constant index */
    const val MIN_HEIGHT = 130
    const val MAX_HEIGHT = 250
    const val MAX_WEIGHT = 120
    const val MIN_WEIGHT = 30
    const val MIN_AGE = 13
    const val MAX_AGE = 80
    const val MIN_DIFFERENCE_WEIGHT = 1
    const val MAX_DIFFERENCE_WEIGHT = 10

    /* Goal */
    const val GAIN_MUSCLE = "Gain muscle"
    const val LOSE_WEIGHT = "Lose weight"
    const val GET_HEALTHIER = "Get healthier"
    const val IMPROVE_MOOD = "Improve mood"
    const val DEFAULT_TIME_TO_REACH_GOAL_IN_WEEK = 0
    const val DEFAULT_WEIGHT_DIFFERENCE_IN_KG = 0
    const val DEFAULT_CALORIES_TO_BURN_EACH_DAY = 500
    const val DEFAULT_CALORIES_TO_BURN_EACH_DAY_TO_GAIN_MUSCLE = 600
    const val DEFAULT_CALORIES_TO_BURN_EACH_DAY_TO_LOSE_WEIGHT = 700
    const val DEFAULT_TIME_FOR_EACH_GOAL_IN_WEEK = 8

    /* Step count */
    const val STEP_GOAL_FOR_GAIN_MUSCLE = 10_000
    const val STEP_GOAL_FOR_LOSE_WEIGHT = 8_000
    const val STEP_GOAL_FOR_GET_HEALTHIER = 6_000
    const val STEP_GOAL_FOR_IMPROVE_MOOD = 6_000

    /* Move time in minutes unit */
    const val MOVE_TIME_FOR_GAIN_MUSCLE = 120
    const val MOVE_TIME_FOR_LOSE_WEIGHT = 120
    const val MOVE_TIME_FOR_GET_HEALTHIER = 90
    const val MOVE_TIME_FOR_IMPROVE_MOOD = 60

    /* BMI constants */
    val BMI_UNDERWEIGHT_RANGE = 0.0..18.5
    val BMI_NORMAL_RANGE = 18.6..24.9
    val BMI_OVERWEIGHT_RANGE = 25.0..29.9
    val BMI_FAT_RANGE = 30.0..35.0
    val BMI_OBESITY_RANGE = 35.1..60.0
}