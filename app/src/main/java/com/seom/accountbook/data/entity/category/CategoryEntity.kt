package com.seom.accountbook.data.entity.category

import android.provider.BaseColumns
import com.seom.accountbook.model.BaseModel

data class CategoryEntity(
    override val id: Long? = null,
    override val name: String, // 카테고리 이름
    val color: Long, // 카테고리 색상
    val type: Int // 카테고리 타입(0: 수입, 1: 지출)
): BaseModel {
    companion object {
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_NAME = "NAME"
        const val COLUMN_NAME_COLOR = "COLOR"
        const val COLUMN_NAME_TYPE = "TYPE"
    }
}
