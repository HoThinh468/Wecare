package com.vn.wecare.feature.home.bmi.usecase

data class BMIUseCase(
    val addBMIHistory: AddBMIHistory,
    val fetchListHistory: FetchListHistory,
    val updateBMIHistory: UpdateBMIHistory,
)