package com.seom.accountbook.data.entity.graph

import com.seom.accountbook.model.graph.OutComeByCategoryModel

data class OutComeByCategoryEntity(
    val id: Long, // categoryId
    val name: String?, // category 이름
    val color: Long?, // category 색
    val count: Long // category 별 지출금액
) {
    fun toModel() = OutComeByCategoryModel(
        id = id,
        name = name ?: "미설정",
        color = if (color != null && color > 0) color else 0xFF242424,
        count = count
    )
}