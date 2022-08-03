package com.seom.accountbook.data.entity.method

import com.seom.accountbook.model.base.BaseModel

data class MethodEntity(
    override val id: Long? = null,
    override val name: String, // 결제 수단 이름
    val type: Int // 0: 수입, 1: 지출
): BaseModel {
    companion object {
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_NAME = "NAME"
        const val COLUMN_NAME_TYPE = "TYPE"
    }
}
