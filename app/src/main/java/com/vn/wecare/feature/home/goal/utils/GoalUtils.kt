package com.vn.wecare.feature.home.goal.utils

import android.annotation.SuppressLint
import com.vn.wecare.feature.home.goal.data.model.EnumGoal
import com.vn.wecare.feature.home.goal.data.model.GoalStatus
import com.vn.wecare.feature.home.goal.data.model.GoalWeeklyRecord
import com.vn.wecare.utils.WecareUserConstantValues.DAY_TO_MILLISECONDS
import com.vn.wecare.utils.WecareUserConstantValues.GAIN_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.LOSE_WEIGHT
import com.vn.wecare.utils.WecareUserConstantValues.NUMBER_OF_DAYS_IN_WEEK
import java.text.SimpleDateFormat
import java.util.Date

fun generateGoalWeeklyRecordIdWithGoal(record: GoalWeeklyRecord): String =
    "${record.startDate}_${record.endDate}"

private fun getListOfFirstDayOfWeeksFromCurrentTime(
    currentTime: Long, numberOfWeek: Int
): List<Long> {
    val listOfFirstDayOfWeeks = arrayListOf<Long>()
    var currentDate = currentTime
    for (i in 0 until numberOfWeek) {
        listOfFirstDayOfWeeks.add(currentDate)
        currentDate = currentDate.plus(NUMBER_OF_DAYS_IN_WEEK * DAY_TO_MILLISECONDS)
    }
    return listOfFirstDayOfWeeks
}

fun getListOfWeeklyRecordsWithCurrentTimeAndNumberOfWeek(
    currentTime: Long,
    numberOfWeek: Int,
    weeklyGoalWeight: Float,
    weeklyGoalCalories: Int,
    weeklyGoalCaloriesOut: Int,
    bmr: Int,
    goalName: String
): List<GoalWeeklyRecord> {
    val firstDays = getListOfFirstDayOfWeeksFromCurrentTime(currentTime, numberOfWeek)
    val result = arrayListOf<GoalWeeklyRecord>()
    repeat(firstDays.size) {
        val day = firstDays[it]
        val record = GoalWeeklyRecord(
            startDate = day,
            endDate = day.plus(6 * DAY_TO_MILLISECONDS),
            weeklyGoalWeight = weeklyGoalWeight,
            weeklyCaloriesGoal = weeklyGoalCalories,
            weeklyCaloriesOutGoal = weeklyGoalCaloriesOut,
            status = if (it == 0) GoalStatus.INPROGRESS.value else GoalStatus.NOTSTARTED.value,
            bmr = bmr,
            goalName = goalName
        )
        result.add(record)
    }
    return result
}

fun getGoalEnumWithName(name: String): EnumGoal {
    return when (name) {
        GAIN_WEIGHT -> EnumGoal.GAINWEIGHT
        LOSE_WEIGHT -> EnumGoal.LOSEWEIGHT
        else -> EnumGoal.MAINTAINWEIGHT
    }
}

@SuppressLint("SimpleDateFormat")
fun getDayFromLongWithFormat(dateTime: Long): String {
    val date = Date(dateTime)
    val df = SimpleDateFormat("dd-MM-yyyy")
    return df.format(date)
}

@SuppressLint("SimpleDateFormat")
fun getDayFromLongWithFormat2(dateTime: Long): String {
    val date = Date(dateTime)
    val df = SimpleDateFormat("dd/MM/yyyy")
    return df.format(date)
}

@SuppressLint("SimpleDateFormat")
fun getDayAndTimeFromLongWithFormat(dateTime: Long): String {
    val date = Date(dateTime)
    val df = SimpleDateFormat("dd-MM-yyyy HH:mm")
    return df.format(date)
}