package com.seom.accountbook.data.entity.method

import android.provider.BaseColumns

data class MethodEntity(
    val id: Long? = null,
    val name: String // 결제 수단 이름
) {
    companion object {
        const val COLUMN_NAME_ID = "ID"
        const val COLUMN_NAME_NAME = "NAME"
    }
}
