package com.vn.wecare.utils

import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate

fun getFirstDayOfWeekWithGivenDate(date: LocalDate): LocalDate {
    var firstDayOfWeek = date
    when (date.dayOfWeek) {
        DayOfWeek.SUNDAY -> { /* Do nothing */
        }

        DayOfWeek.MONDAY -> {
            firstDayOfWeek = date.minusDays(1)
        }

        DayOfWeek.TUESDAY -> {
            firstDayOfWeek = date.minusDays(2)
        }

        DayOfWeek.WEDNESDAY -> {
            firstDayOfWeek = date.minusDays(3)
        }

        DayOfWeek.THURSDAY -> {
            firstDayOfWeek = date.minusDays(4)
        }

        DayOfWeek.FRIDAY -> {
            firstDayOfWeek = date.minusDays(5)
        }

        DayOfWeek.SATURDAY -> {
            firstDayOfWeek = date.minusDays(6)
        }

        else -> { /* Do nothing */
        }
    }
    return firstDayOfWeek
}

fun getLastDayOfWeekWithGivenDay(date: LocalDate): LocalDate {
    var lastDayOfWeek = date
    when (date.dayOfWeek) {
        DayOfWeek.SUNDAY -> {
            lastDayOfWeek = date.plusDays(6)
        }

        DayOfWeek.MONDAY -> {
            lastDayOfWeek = date.plusDays(5)
        }

        DayOfWeek.TUESDAY -> {
            lastDayOfWeek = date.plusDays(4)
        }

        DayOfWeek.WEDNESDAY -> {
            lastDayOfWeek = date.plusDays(3)
        }

        DayOfWeek.THURSDAY -> {
            lastDayOfWeek = date.plusDays(2)
        }

        DayOfWeek.FRIDAY -> {
            lastDayOfWeek = date.plusDays(1)
        }

        DayOfWeek.SATURDAY -> { /* Do nothing */
        }

        else -> { /* Do nothing */
        }
    }
    return lastDayOfWeek
}

fun getListOfDayWithStartAndEndDay(startDay: LocalDate, endDay: LocalDate): List<LocalDate> {
    val days = arrayListOf<LocalDate>()
    val duration = Duration.between(startDay.atStartOfDay(), endDay.atStartOfDay()).toDays()
    for (i in 0..duration) {
        days.add(startDay.plusDays(i))
    }
    return days
}