package com.vn.wecare.feature.onboarding.model

import com.vn.wecare.R

enum class MaleModelInfo(val fullName: String, val height: Int, val weight: Int, val bmi: Float, val imgSrc: Int) {
    BRUNOMARS("Bruno Mars", 165, 67, 24.6f, R.drawable.img_bruno_mars), TOMHOLLAND(
        "Tom Holland", 173, 64, 21.4f, R.drawable.img_tom_holland
    ),
    CHRISPINE(
        "Chris Pine",
        183,
        79,
        23.1f,
        R.drawable.img_chris_pine
    ),
    CHRISHEMSWORTH("Chris Hemsworth", 193, 91, 24.4f, R.drawable.img_chris_hemsworth)
}