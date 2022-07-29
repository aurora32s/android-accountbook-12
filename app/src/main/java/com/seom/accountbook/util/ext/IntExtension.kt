package com.seom.accountbook.util.ext

import java.text.DecimalFormat

val formatter = DecimalFormat("#,###")
fun Int.toMoney() = formatter.format(this)
