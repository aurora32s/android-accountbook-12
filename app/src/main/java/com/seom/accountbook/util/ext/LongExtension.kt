package com.seom.accountbook.util.ext

fun Long.toMoney(won: Boolean = false): String = "${formatter.format(this)} ${if (won) "원" else ""}"
