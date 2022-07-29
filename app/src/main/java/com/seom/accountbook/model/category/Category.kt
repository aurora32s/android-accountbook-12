package com.seom.accountbook.model.category

import com.seom.accountbook.model.BaseModel

data class Category(
    override val id: Int,
    override val name: String,
    val categoryColor: Long,
) : BaseModel