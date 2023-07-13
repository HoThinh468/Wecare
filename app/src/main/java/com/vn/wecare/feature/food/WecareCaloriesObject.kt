package com.vn.wecare.feature.food

import com.vn.wecare.core.WecareUserSingletonObject
import com.vn.wecare.utils.WecareUserConstantValues.GAIN_MUSCLE
import com.vn.wecare.utils.WecareUserConstantValues.GET_HEALTHIER
import com.vn.wecare.utils.WecareUserConstantValues.IMPROVE_MOOD
import com.vn.wecare.utils.WecareUserConstantValues.LOSE_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MIN_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UserCaloriesAmount(
    val caloriesInEachDay: Int = 0,
    val caloriesOutEachDay: Int = 0,
    val caloriesOfBreakfast: Int = 0,
    val caloriesOfLunch: Int = 0,
    val caloriesOfSnack: Int = 0,
    val caloriesOfDinner: Int = 0,
)

object WecareCaloriesObject {
    private val instance = MutableStateFlow(UserCaloriesAmount())

    fun getInstance() = instance.value

    fun getInstanceFlow() = instance.asStateFlow()

    fun updateUserCaloriesAmount() {
        WecareUserSingletonObject.getInstance().let { user ->
            val basicCalories = getBasicCaloriesAmount(
                user.weight ?: MIN_WEIGHT,
                user.height ?: MIN_HEIGHT,
                user.gender ?: true,
                user.age ?: MIN_AGE
            )
            val caloriesOut = getCaloriesOutAmount(basicCalories, user.goal ?: IMPROVE_MOOD)
            val caloriesIn = getCaloriesIn(caloriesOut, user.goal ?: IMPROVE_MOOD)
            updateCaloriesOutAmountOfDay(caloriesOut)
            updateCaloriesInAmountOfDay(caloriesIn)
        }
    }

    fun getBasicCaloriesAmount(
        weight: Int, height: Int, gender: Boolean, age: Int
    ): Int {
        return if (gender) {
            (6.25 * height).toInt() + (10 * weight) - (5 * age) + 5
        } else {
            (6.25 * height).toInt() + (10 * weight) - (5 * age) - 161
        }
    }

    fun getCaloriesOutAmount(
        basicCalories: Int, goal: String
    ): Int {
        return when (goal) {
            GAIN_MUSCLE -> {
                (basicCalories * 1.725).toInt()
            }

            LOSE_WEIGHT, GET_HEALTHIER -> {
                (basicCalories * 1.55).toInt()
            }

            else -> {
                (basicCalories * 1.375).toInt()
            }
        }
    }

    fun getCaloriesIn(caloriesOut: Int, goal: String): Int {
        return when (goal) {
            GAIN_MUSCLE -> {
                caloriesOut + 1100
            }

            LOSE_WEIGHT -> {
                caloriesOut - 1100
            }

            else -> {
                caloriesOut
            }
        }
    }

    private fun updateCaloriesInAmountOfDay(calories: Int) {
        instance.update {
            it.copy(
                caloriesInEachDay = calories,
                caloriesOfBreakfast = getCaloriesForBreakfast(calories),
                caloriesOfLunch = getCaloriesForLunch(calories),
                caloriesOfSnack = getCaloriesForSnack(calories),
                caloriesOfDinner = getCaloriesForDinner(calories)
            )
        }
    }



    private fun updateCaloriesOutAmountOfDay(caloriesOut: Int) {
        instance.update { it.copy(caloriesOutEachDay = caloriesOut) }
    }

    private fun getCaloriesForBreakfast(caloriesIn: Int) = caloriesIn.times(0.2).toInt()

    private fun getCaloriesForLunch(caloriesIn: Int) = caloriesIn.times(0.35).toInt()

    private fun getCaloriesForDinner(caloriesIn: Int) = caloriesIn.times(0.35).toInt()

    private fun getCaloriesForSnack(caloriesIn: Int) = caloriesIn.times(0.1).toInt()
}