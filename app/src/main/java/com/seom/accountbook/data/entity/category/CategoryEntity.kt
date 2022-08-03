package com.seom.accountbook.data.entity.category

import com.seom.accountbook.model.base.BaseModel
import com.seom.accountbook.model.category.CategoryModel
import com.seom.accountbook.model.history.HistoryType

data class CategoryEntity(
    val id: Long? = null,
    val name: String, // 카테고리 이름
    val color: Long, // 카테고리 색상
    val type: Int // 카테고리 타입(0: 수입, 1: 지출)
) {
    fun toModel() =
        CategoryModel(
            id = id!!,
            name = name,
            categoryColor = color,
            type = HistoryType.getHistoryType(type)
        )

    companion object {
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_NAME = "NAME"
        const val COLUMN_NAME_COLOR = "COLOR"
        const val COLUMN_NAME_TYPE = "TYPE"
    }
}
