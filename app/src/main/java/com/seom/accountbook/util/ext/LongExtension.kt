package com.seom.accountbook.util.ext
import java.text.DecimalFormat

fun Long.toMoney(): String {
    if (this == 0L) return ""
    else return formatter.format(this)
}
