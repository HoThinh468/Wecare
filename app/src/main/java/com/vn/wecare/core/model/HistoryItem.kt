package com.vn.wecare.core.model

data class HistoryItem(
    val type: ExerciseType = ExerciseType.None,
    val kcal: Float = 0f,
    val duration: Int = 0,
    val time: Long = 0
)