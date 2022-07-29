package com.seom.accountbook.model.method

import com.seom.accountbook.model.BaseModel

data class Method(
    override val id: Int,
    override val name: String
): BaseModel
