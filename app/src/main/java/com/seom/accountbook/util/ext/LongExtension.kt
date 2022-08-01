package com.seom.accountbook.util.ext
import java.text.DecimalFormat

fun Long.toMoney(): String = formatter.format(this)
