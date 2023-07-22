package com.vn.wecare.feature.food.data.model.Recipe

import com.squareup.moshi.Json

data class Instruction(
    @Json(name = "name") val name: String = "",
    @Json(name = "steps") val steps: List<CookingSteps> = emptyList()
)
