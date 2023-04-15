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
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "2High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "2High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "3High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "4High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "4High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "5High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "5High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "6High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "6High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "7High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "7High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    )
)

val listStrength: List<ListExerciseItem> = listOf(
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "3High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "4High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "4High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "5High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "5High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "6High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "6High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailStrength
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "7High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailStrength
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailStrength
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailStrength
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailStrength
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailStrength
    ),
    ListExerciseItem(
        type = ExerciseType.Strength,
        description = "High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailStrength
    )
)

val listFullBody: List<ListExerciseItem> = listOf(
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "1High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "1High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "2High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "2High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "3High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "4High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "4High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "5High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "5High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "6High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "6High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "7High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "7High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "1High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "1High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "2High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "2High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "3High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "4High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "4High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "5High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "5High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "6High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "6High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "7High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "7High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.FullBody,
        description = "1High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "1High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "2High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "2High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "3High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "4High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "4High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "5High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "5High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "6High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "6High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "7High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "7High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "1High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "1High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "2High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "2High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "3High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "3High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "4High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "4High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "5High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "5High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "6High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "6High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    ),
    ListExerciseItem(
        type = ExerciseType.Endurance,
        description = "7High intensity full body workout High intensity full body workout High intensity full body workout High intensity full body workout",
        image = R.drawable.exercise_header,
        title = "7High intensity full body...",
        level = ExerciseLevel.Easy,
        duration = 30,
        listExerciseDetail = listDetailEndurance
    )
)