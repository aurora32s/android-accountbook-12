package com.seom.accountbook.util.ext

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.fullFormat(): String {
    val year = this.year
    val month = this.month.value
    val date = this.dayOfMonth
    val dateOfWeek = this.dayOfWeekText()

    return "${year}. ${month}. ${date} ${dateOfWeek}"
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.dayOfWeekText() = this.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())