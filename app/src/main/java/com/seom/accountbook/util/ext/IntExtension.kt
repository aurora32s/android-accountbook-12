package com.seom.accountbook.util.ext

import java.text.DecimalFormat

val formatter = DecimalFormat("#,###")
fun Int.toMoney(): String {
    if (this == 0) return ""
    return formatter.format(this)
}
