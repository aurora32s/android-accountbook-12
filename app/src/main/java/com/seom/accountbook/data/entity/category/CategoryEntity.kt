package com.seom.accountbook.data.entity.category

import android.provider.BaseColumns

data class CategoryEntity(
    val name: String, // 카테고리 이름
    val color: Long, // 카테고리 색상
    val type: Int // 카테고리 타입(0: 수입, 1: 지출)
) : BaseColumns {
    companion object {
        const val COLUMN_NAME_NAME = "NAME"
        const val COLUMN_NAME_COLOR = "COLOR"
        const val COLUMN_NAME_TYPE = "TYPE"
    }
}
