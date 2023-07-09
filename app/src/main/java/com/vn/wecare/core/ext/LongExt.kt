package com.vn.wecare.core.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateAndTime(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}