package com.seom.accountbook.model.post

import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.model.setting.SettingModel

data class PostModel(
    val account: Result<AccountEntity>?,
    val settingModel: SettingModel
)
