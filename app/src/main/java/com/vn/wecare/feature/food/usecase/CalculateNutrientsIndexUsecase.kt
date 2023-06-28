package com.vn.wecare.feature.food.usecase

import javax.inject.Inject

class CalculateNutrientsIndexUsecase @Inject constructor() {
    fun getProteinIndexInGram(calories: Int) = ((calories * 0.6).toInt() / 4)

    fun getCarbIndexInGram(calories: Int) = ((calories * 0.5).toInt() / 4)

    fun getFatIndexInGram(calories: Int) = ((calories * 0.4).toInt() / 9)

    fun getCaloriesFromNutrients(protein: Int, fat: Int, carbs: Int) =
        protein * 4 + fat * 9 + carbs * 4
}