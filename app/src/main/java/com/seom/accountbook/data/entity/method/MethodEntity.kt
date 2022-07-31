package com.seom.accountbook.data.entity.method

import android.provider.BaseColumns
import com.seom.accountbook.model.BaseModel

data class MethodEntity(
    override val id: Long? = null,
    override val name: String // 결제 수단 이름
): BaseModel {
    companion object {
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_NAME = "NAME"
    }
}
