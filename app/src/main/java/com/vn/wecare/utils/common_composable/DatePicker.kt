package com.vn.wecare.utils.common_composable

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.util.*

fun datePicker(
    updateDate: (year: Int, month: Int, dayOfMonth: Int) -> Unit, context: Context
): DatePickerDialog {

    val mCalendar = Calendar.getInstance()

    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        context, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            updateDate(mYear, mMonth + 1, mDayOfMonth)
        }, mYear, mMonth, mDay
    )

    return mDatePickerDialog
}