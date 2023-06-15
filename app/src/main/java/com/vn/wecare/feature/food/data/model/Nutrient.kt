package com.vn.wecare.feature.food.data.model

import com.squareup.moshi.Json

data class Nutrient(
    @Json(name = "name") val name: String = "",
    @Json(name = "amount") val amount: Float = 0f,
    @Json(name = "unit") val unit: String = "g"
)
