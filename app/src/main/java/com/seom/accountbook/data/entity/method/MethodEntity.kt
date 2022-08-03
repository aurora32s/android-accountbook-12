package com.seom.accountbook.data.entity.method

import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.model.method.MethodModel

data class MethodEntity(
    val id: Long? = null,
    val name: String, // 결제 수단 이름
    val type: Int // 0: 수입, 1: 지출
) {
    fun toModel() = MethodModel(
        id = id!!,
        name = name,
        type = HistoryType.getHistoryType(type)
    )

    companion object {
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_NAME = "NAME"
        const val COLUMN_NAME_TYPE = "TYPE"
    }
}
