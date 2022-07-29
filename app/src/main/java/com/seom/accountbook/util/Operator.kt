package com.seom.accountbook.util

/**
 * 기본 데이터 타입의 가공
 */

/**
 * 해당 월의 최대 일ß자
 */
fun Int.getMaxDate(): Int = when (this) {
    1, 3, 5, 7, 8, 10, 12 -> 31
    else -> 30
}