package com.vn.wecare.feature.home.bmi.usecase

import com.vn.wecare.feature.home.bmi.service.BMIServices

class FetchListHistory (
    private val services: BMIServices
) {
    suspend operator fun invoke() =
        services.fetchListHistory()
}