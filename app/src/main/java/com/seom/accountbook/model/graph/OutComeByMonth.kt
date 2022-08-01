package com.seom.accountbook.model.graph

import com.seom.accountbook.model.BaseCount

data class OutComeByMonth(
    val count: Long, // 월별 지출
    val name: String // 월
)
