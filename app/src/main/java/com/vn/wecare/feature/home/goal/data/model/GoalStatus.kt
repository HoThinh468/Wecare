package com.vn.wecare.feature.home.goal.data.model

enum class GoalStatus(val value: String) {
    NOTSTARTED("Not started"), INPROGRESS("In progress"), CANCELED("Canceled"), EXPIRED("Expired"), DONE(
        "Done"
    ),
    QUITED("Quited")
}