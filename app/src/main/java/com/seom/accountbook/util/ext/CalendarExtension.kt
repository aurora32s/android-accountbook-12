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