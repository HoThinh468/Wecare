package com.vn.wecare.feature.food.data.model

import com.squareup.moshi.Json

data class MealNameSearchResult(
    @Json(name = "results") val results: List<MealByName>
)