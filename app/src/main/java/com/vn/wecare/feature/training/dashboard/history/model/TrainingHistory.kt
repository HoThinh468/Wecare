package com.vn.wecare.feature.training.dashboard.history.model

import com.vn.wecare.feature.training.utils.UserAction

data class TrainingHistory(
    val userAction: String = "WALKING",
    val time: Long = 0,
    val duration: Int = 0,
    val kcal: Double = 0.0,
    val distance: Double = 0.0
)

data class TotalDailyTrainingHistory(
    val time: Long,
    val duration: Int,
    val kcal: Double
)

data class TotalTrainingHistory(
    val duration: Int,
    val kcal: Double
)
