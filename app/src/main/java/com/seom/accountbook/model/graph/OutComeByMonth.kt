package com.seom.accountbook.model.graph

import com.seom.accountbook.model.base.BaseLinearGraphModel

data class OutComeByMonth(
    override val count: Long, // 월별 지출
    override val name: String // 월
): BaseLinearGraphModel
