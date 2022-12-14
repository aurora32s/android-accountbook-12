package com.seom.accountbook.model.graph

import com.seom.accountbook.model.base.BaseColorGraphModel

data class OutComeByCategoryModel(
    override val id: Long, // categoryId
    override val name: String, // category 이름
    override val color: Long, // category 색
    override val count: Long // category 별 지출금액
) : BaseColorGraphModel
