package com.seom.accountbook.util.ext

import com.seom.accountbook.model.common.Date


/**
 * curtom Date 관련 확장 함수
 */

fun Date.format(): String = "${year}년 ${month}월"