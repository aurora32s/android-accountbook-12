package com.seom.accountbook.util.ext
import java.text.DecimalFormat

fun Long.toMoney() = formatter.format(this)
