package com.seom.accountbook.util.ext

import java.util.*

/**
 * Util Calendar 관련 extension
 */

fun Calendar.format(): String {
    val year = this.get(Calendar.YEAR)
    val month = this.get(Calendar.MONTH)

    return "${year}년 ${month}월"
}

fun Calendar.fullForamt(): String {
    val year = this.get(Calendar.YEAR)
    val month = this.get(Calendar.MONTH)
    val date = this.get(Calendar.DATE)
    val dateOfWeek = when (this.get(Calendar.DAY_OF_WEEK)) {
        1 -> "월"
        2 -> "화"
        3 -> "수"
        4 -> "목"
        5 -> "금"
        6 -> "토"
        else -> "일"
    }

    return "${year}. ${month}. ${date} ${dateOfWeek}"
}