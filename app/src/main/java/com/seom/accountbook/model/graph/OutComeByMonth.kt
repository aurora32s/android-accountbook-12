package com.seom.accountbook.model.graph

import com.seom.accountbook.model.BaseCount

data class OutComeByMonth(
    override val id: Long,
    override val count: Long, // 월별 지출
    override val color: Long,
    override val name: String // 월
) : BaseCount
