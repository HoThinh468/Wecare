package com.vn.wecare.feature.food

import com.vn.wecare.core.WecareUserSingleton
import com.vn.wecare.utils.WecareUserConstantValues.GAIN_MUSCLE
import com.vn.wecare.utils.WecareUserConstantValues.GET_HEALTHIER
import com.vn.wecare.utils.WecareUserConstantValues.IMPROVE_MOOD
import com.vn.wecare.utils.WecareUserConstantValues.LOOSE_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_AGE
import com.vn.wecare.utils.WecareUserConstantValues.MIN_HEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.MIN_WEIGHT
import kotlinx.coroutines.coroutineScope
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

    private val wecareUser = WecareUserSingleton.getInstanceFlow()

    private val instance = MutableStateFlow(UserCaloriesAmount())

    fun getInstance() = instance.value

    fun getInstanceFlow() = instance.asStateFlow()

    suspend fun calculateUserCaloriesAmount() = coroutineScope {
        wecareUser.collect { user ->
            updateCaloriesInAmountOfDay(
                user.goal ?: IMPROVE_MOOD,
                user.weight ?: MIN_WEIGHT,
                user.height ?: MIN_HEIGHT,
                user.gender ?: true,
                user.age ?: MIN_AGE
            )
        }
    }

    private fun updateCaloriesInAmountOfDay(
        goal: String, weight: Int, height: Int, gender: Boolean, age: Int
    ) {
        var calories = if (gender) {
            (6.25 * height).toInt() + (10 * weight) - (5 * age) + 5
        } else {
            (6.25 * height).toInt() + (10 * weight) - (5 * age) - 161
        }
        calories = when (goal) {
            GAIN_MUSCLE, LOOSE_WEIGHT -> {
                (calories * 1.725).toInt()
            }

            GET_HEALTHIER -> {
                (calories * 1.55).toInt()
            }

            else -> {
                (calories * 1.375).toInt()
            }
        }
        instance.update {
            it.copy(
                caloriesInEachDay = calories,
                caloriesOfBreakfast = getCaloriesForBreakfast(calories),
                caloriesOfLunch = getCaloriesForLunch(calories),
                caloriesOfSnack = getCaloriesForSnack(calories),
                caloriesOfDinner = getCaloriesForDinner(calories)
            )
        }
        updateCaloriesOutAmountOfDay(calories, goal)
    }

    private fun updateCaloriesOutAmountOfDay(caloriesIn: Int, goal: String) {
        val caloriesOut = when (goal) {
            LOOSE_WEIGHT -> {
                caloriesIn - 500
            }

            GAIN_MUSCLE -> {
                caloriesIn.times(1.1).toInt()
            }

            else -> {
                caloriesIn
            }
        }
        instance.update { it.copy(caloriesOutEachDay = caloriesOut) }
    }

    private fun getCaloriesForBreakfast(caloriesIn: Int) = caloriesIn.times(0.2).toInt()

    private fun getCaloriesForLunch(caloriesIn: Int) = caloriesIn.times(0.35).toInt()

    private fun getCaloriesForDinner(caloriesIn: Int) = caloriesIn.times(0.35).toInt()

    private fun getCaloriesForSnack(caloriesIn: Int) = caloriesIn.times(0.1).toInt()
}