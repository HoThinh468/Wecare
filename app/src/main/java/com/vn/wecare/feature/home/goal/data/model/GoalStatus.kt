package com.vn.wecare.feature.home.goal.data.model

enum class GoalStatus(val value: String, val minProgress: Float?) {
    NOTSTARTED("Not started", 0f), INPROGRESS("In progress", null), CANCELED(
        "Canceled", null
    ),
    SUCCESS(
        "Success", 1.0f
    ),
    DONE(
        "Done", 0.8f
    )
}