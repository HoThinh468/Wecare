package com.vn.wecare.core.model

import com.vn.wecare.R
import com.vn.wecare.feature.exercises.exercise_list.ExerciseLevel
import java.io.Serializable

enum class ExerciseType {
    None, Endurance, Strength, Balance, Flexibility, FullBody
}

data class ListExerciseItem(
    val type: ExerciseType,
    val description: String,
    val image: Int,
    val title: String,
    val level: ExerciseLevel,
    val duration: Int,
    val listExerciseDetail: List<ProgramDetailItem>
): Serializable

val listEndurance: List<ListExerciseItem> = listOf(
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "1High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "1High intensity full body...",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "2High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "2High intensity full body...",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "3High intensity full body...",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "4High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "4High intensity full body...",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "5High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "5High intensity full body...",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "6High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "6High intensity full body...",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "7High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "7High intensity full body...",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    )
)

val listStrength: List<ListExerciseItem> = listOf(
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "Strength your chest - Beginner",
        image = R.drawable.exercise_header,
        title = "Strength your chest - Beginner",
        level = ExerciseLevel.Beginner,
        duration = 10,
        listExerciseDetail = listFullBodyFirstDay
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "Strength your chest - Intermediate",
        image = R.drawable.exercise_header,
        title = "Strength your chest - Intermediate",
        level = ExerciseLevel.Intermediate,
        duration = 25,
        listExerciseDetail = listFullBodySecondDay
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "5High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Strength your chest - Advanced",
        level = ExerciseLevel.Advanced,
        duration = 30,
        listExerciseDetail = listFullBodyFirstDay
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "6High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Strength your abs - Beginner",
        level = ExerciseLevel.Beginner,
        duration = 15,
        listExerciseDetail = listFullBodySecondDay
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Strength your abs - Intermediate",
        level = ExerciseLevel.Intermediate,
        duration = 20,
        listExerciseDetail = listFullBodyFirstDay
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Strength your abs - Advanced",
        level = ExerciseLevel.Advanced,
        duration = 28,
        listExerciseDetail = listFullBodySecondDay
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Strength your arm - Beginner",
        level = ExerciseLevel.Beginner,
        duration = 12,
        listExerciseDetail = listFullBodyFirstDay
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Strength your arm - Intermediate",
        level = ExerciseLevel.Intermediate,
        duration = 23,
        listExerciseDetail = listFullBodySecondDay
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Strength your arm - Advanced",
        level = ExerciseLevel.Advanced,
        duration = 27,
        listExerciseDetail = listFullBodyFirstDay
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title ="Strength your leg - Beginner",
        level = ExerciseLevel.Beginner,
        duration = 16,
        listExerciseDetail = listFullBodySecondDay
    )
)

val listFullBody: List<ListExerciseItem> = listOf(
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "1High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 1 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 10,
        listExerciseDetail = listFullBodyFirstDay
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "2High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 2 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listFullBodySecondDay
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 3 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 4 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 5 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 6 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 7 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 8 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 9 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 10 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 11 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 12 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 13 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 14 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 15 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 16 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 17 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 18 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 19 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 20 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 21 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 22 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 23 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 24 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 25 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 26 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 27 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "Day 28 - Full Body",
        level = ExerciseLevel.Beginner,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
)