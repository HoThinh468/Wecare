package com.vn.wecare.feature.home.bmi.usecase

import com.vn.wecare.feature.home.bmi.service.BMIServices

class AddBMIHistory(
    private val services: BMIServices
) {
    suspend operator fun invoke(age: Int, gender: Boolean, height: Int?, weight: Int?) =
        services.addBMIHistory(age, gender, height, weight)
}
