package com.vn.wecare.feature.home.bmi.model;

data class BMIHistoryEntity(
    val height: Int,
    val weight: Int,
    val age: Int,
    val gender: Boolean,
    val time: Long
)