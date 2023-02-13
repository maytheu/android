package com.maytheu.note.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("EE, d MMM hh:mm aaa", Locale.getDefault())
    return format.format(date)
}