package com.seom.accountbook.data.entity.method

import android.provider.BaseColumns

data class MethodEntity(
    val name: String // 결제 수단 이름
): BaseColumns {
    companion object {
        const val COLUMN_NAME_NAME = "NAME"
    }
}
