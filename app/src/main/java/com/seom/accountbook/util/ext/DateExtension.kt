package com.seom.accountbook.util.ext

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Date 관련 확장 함수
 */

fun Date.format(pattern: String): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}

fun Date.toYearAndMonth() = this.format("YYYY년 MM월")