package com.vn.wecare.feature.food.usecase

import javax.inject.Inject

class CalculateNutrientsIndexUsecase @Inject constructor() {
    fun getProteinIndexInGram(calories: Int) = (calories / 4)

    fun getCarbIndexInGram(calories: Int) = (calories / 4)

    fun getFatIndexInGram(calories: Int) = (calories / 9)
}