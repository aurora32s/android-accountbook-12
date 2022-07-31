package com.seom.accountbook.model.setting

import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.data.entity.Result

data class SettingModel(
    val methods: Result<List<MethodEntity>>,
    val categories: Result<List<CategoryEntity>>
)
