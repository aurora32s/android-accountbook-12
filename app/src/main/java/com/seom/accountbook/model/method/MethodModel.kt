package com.seom.accountbook.model.method

import com.seom.accountbook.model.base.BaseModel

data class MethodModel(
    override val id: Long,
    override val name: String
): BaseModel
