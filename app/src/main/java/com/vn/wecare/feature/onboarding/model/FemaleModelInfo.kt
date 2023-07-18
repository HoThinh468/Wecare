package com.vn.wecare.feature.onboarding.model

import com.vn.wecare.R

enum class FemaleModelInfo(
    val fullName: String, val height: Int, val weight: Int, val bmi: Float, val imgSrc: Int
) {
    ARIANAGRANDE("Ariana Grande", 154, 48, 20.2f, R.drawable.img_ariana_grande), EMMAWATSON(
        "Emma Watson", 165, 53, 19.5f, R.drawable.img_emma_watson
    ),
    TAYLORSWIFT("Taylor Swift", 178, 59, 18.6f, R.drawable.img_taylor_swift)
}