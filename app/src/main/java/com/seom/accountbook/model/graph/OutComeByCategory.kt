package com.seom.accountbook.model.graph

import com.seom.accountbook.model.BaseCount

data class OutComeByCategory(
    override val id: Int, // categoryId
    override val name: String, // category 이름
    override val color: Long, // category 색
    override val count: Long // category 별 지출금액
) : BaseCount
