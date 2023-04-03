package com.vn.wecare.core.model

import com.vn.wecare.R
import com.vn.wecare.feature.exercises.exercise_list.ExerciseLevel

data class ProgramDetailItem(
    val image: Int,
    val title: String,
    val reps: Int,
    val duration: Int
)

val listTestProgramDetail: Array<ProgramDetailItem> = arrayOf(
    ProgramDetailItem(
        image = R.drawable.exercise_header,
        title = "title",
        reps = 20,
        duration = 20,
    ),
    ProgramDetailItem(
        image = R.drawable.exercise_header,
        title = "title",
        reps = 20,
        duration = 20,
    ),
    ProgramDetailItem(
        image = R.drawable.exercise_header,
        title = "title",
        reps = 20,
        duration = 20,
    ),
    ProgramDetailItem(
        image = R.drawable.exercise_header,
        title = "title",
        reps = 20,
        duration = 20,
    ),
    ProgramDetailItem(
        image = R.drawable.exercise_header,
        title = "title",
        reps = 20,
        duration = 20,
    ),
    ProgramDetailItem(
        image = R.drawable.exercise_header,
        title = "title",
        reps = 20,
        duration = 20,
    ),
    ProgramDetailItem(
        image = R.drawable.exercise_header,
        title = "title",
        reps = 20,
        duration = 20,
    ),
    ProgramDetailItem(
        image = R.drawable.exercise_header,
        title = "title",
        reps = 20,
        duration = 20,
    ),
    ProgramDetailItem(
        image = R.drawable.exercise_header,
        title = "title",
        reps = 20,
        duration = 20,
    ),
)