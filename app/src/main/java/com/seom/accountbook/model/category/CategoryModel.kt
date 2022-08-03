package com.seom.accountbook.model.category

import com.seom.accountbook.model.base.BaseModel

data class CategoryModel(
    override val id: Long,
    override val name: String,
    val categoryColor: Long,
) : BaseModel