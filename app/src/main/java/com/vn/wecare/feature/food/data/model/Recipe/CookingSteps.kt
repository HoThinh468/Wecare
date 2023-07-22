package com.vn.wecare.feature.food.data.model.Recipe

import com.squareup.moshi.Json

data class CookingSteps(
    @Json(name = "number") val number: Int = 0,
    @Json(name = "step") val step: String = ""
)
