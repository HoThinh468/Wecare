package com.vn.wecare.feature.home.goal.data.model

import androidx.compose.ui.graphics.Color
import com.vn.wecare.ui.theme.Blue
import com.vn.wecare.ui.theme.Green300
import com.vn.wecare.ui.theme.Green500
import com.vn.wecare.ui.theme.Grey500
import com.vn.wecare.ui.theme.Red400

enum class GoalStatus(val value: String, val minProgress: Float?, val color: Color) {
    NOTSTARTED("Not started", 0f, Grey500), INPROGRESS("In progress", null, Blue), CANCELED(
        "Canceled", null, Red400
    ),
    SUCCESS(
        "Success", 1.0f, Green500
    ),
    DONE(
        "Done", 0.8f, Green300
    );

    companion object {
        fun getGoalStatusFromString(value: String): GoalStatus {
            return when (value) {
                "Not started" -> NOTSTARTED
                "In progress" -> INPROGRESS
                "Canceled" -> CANCELED
                "Success" -> SUCCESS
                else -> DONE
            }
        }
    }
}