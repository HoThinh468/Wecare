package com.vn.wecare.feature.home.goal.data.model

import androidx.compose.ui.graphics.Color
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Green300
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.Red400

enum class GoalStatus(val value: String, val color: Color) {
    NOTSTARTED("Not started", Grey500), INPROGRESS("In progress", Blue), CANCELED(
        "Canceled", Red400
    ),
    DONE(
        "Done", Green500
    );

    companion object {
        fun getGoalStatusFromValue(value: String): GoalStatus {
            return when (value) {
                "Not started" -> NOTSTARTED
                "In progress" -> INPROGRESS
                "Canceled" -> CANCELED
                else -> DONE
            }
        }
    }
}