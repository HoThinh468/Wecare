package com.vn.wecare.feature.food.data.model

import com.squareup.moshi.Json

data class Nutrition(
    @Json(name = "nutrients") val nutrients: List<Nutrient> = emptyList()
)