package com.seom.accountbook.model.category

import com.seom.accountbook.model.base.BaseModel
import com.seom.accountbook.model.history.HistoryType

data class CategoryModel(
    override val id: Long,
    override val name: String,
    val categoryColor: Long,
    val type: HistoryType
) : BaseModel