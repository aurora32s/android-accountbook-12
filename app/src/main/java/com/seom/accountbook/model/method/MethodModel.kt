package com.seom.accountbook.model.method

import com.seom.accountbook.model.base.BaseModel
import com.seom.accountbook.model.history.HistoryType

data class MethodModel(
    override val id: Long,
    override val name: String,
    val type: HistoryType
): BaseModel
