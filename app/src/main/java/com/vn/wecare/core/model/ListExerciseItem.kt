package com.vn.wecare.core.model

import com.vn.wecare.R
import com.vn.wecare.feature.exercises.exercise_list.ExerciseLevel

data class ListExerciseItem(
    val image: Int,
    val title: String,
    val level: ExerciseLevel,
    val duration: Int,
    val isLiked: Boolean
)

val listTest: Array<ListExerciseItem> = arrayOf(
    ListExerciseItem(
        image = R.drawable.exercise_header,
        title = "High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        isLiked = false
    ),
    ListExerciseItem(
        image = R.drawable.exercise_header,
        title = "High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        isLiked = false
    ),
    ListExerciseItem(
        image = R.drawable.exercise_header,
        title = "High intensity full body with many modern equipments",
        level = ExerciseLevel.Easy,
        duration = 30,
        isLiked = true
    ),
    ListExerciseItem(
        image = R.drawable.exercise_header,
        title = "High intensity full body with many modern equipments",
        level = ExerciseLevel.Easy,
        duration = 30,
        isLiked = true
    ),
    ListExerciseItem(
        image = R.drawable.exercise_header,
        title = "High intensity full body with many modern equipments",
        level = ExerciseLevel.Easy,
        duration = 30,
        isLiked = true
    ),
    ListExerciseItem(
        image = R.drawable.exercise_header,
        title = "High intensity full body with many modern equipments",
        level = ExerciseLevel.Easy,
        duration = 30,
        isLiked = true
    ),
    ListExerciseItem(
        image = R.drawable.exercise_header,
        title = "High intensity full body with many modern equipments",
        level = ExerciseLevel.Easy,
        duration = 30,
        isLiked = true
    ),
    ListExerciseItem(
        image = R.drawable.exercise_header,
        title = "High intensity full body with many modern equipments",
        level = ExerciseLevel.Easy,
        duration = 30,
        isLiked = true
    ),
    ListExerciseItem(
        image = R.drawable.exercise_header,
        title = "High intensity full body with many modern equipments",
        level = ExerciseLevel.Easy,
        duration = 30,
        isLiked = true
    ),
)