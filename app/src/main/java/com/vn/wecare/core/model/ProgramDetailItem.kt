package com.vn.wecare.core.model

import com.vn.wecare.R

data class ProgramDetailItem(
    val exercise: Int,
    val title: String,
    val rest: Int,
    val duration: Int
)

val errorList: List<ProgramDetailItem> = listOf(
    ProgramDetailItem(
        exercise = R.drawable.jumping_jack,
        title = "Error Loading",
        rest = 0,
        duration = 0,
    )
)

val listDetailEndurance: List<ProgramDetailItem> = listOf(
    ProgramDetailItem(
        exercise = R.drawable.jumping_jack,
        title = "Jumping Jack",
        rest = 3,
        duration = 10,
    ),
    ProgramDetailItem(
        exercise = R.drawable.sit_3_4_up,
        title = "3/4 Sit Up",
        rest = 5,
        duration = 10,
    ),
    ProgramDetailItem(
        exercise = R.drawable.air_bike,
        title = "Air Bike",
        rest = 5,
        duration = 10,
    ),
)

val listDetailStrength: List<ProgramDetailItem> = listOf(
    ProgramDetailItem(
        exercise = R.drawable.exercise_header,
        title = "title2",
        rest = 15,
        duration = 20,
    ),
    ProgramDetailItem(
        exercise = R.drawable.exercise_header,
        title = "title3",
        rest = 15,
        duration = 20,
    ),
    ProgramDetailItem(
        exercise = R.drawable.exercise_header,
        title = "title4",
        rest = 15,
        duration = 20,
    ),
    ProgramDetailItem(
        exercise = R.drawable.exercise_header,
        title = "title5",
        rest = 15,
        duration = 20,
    )
)