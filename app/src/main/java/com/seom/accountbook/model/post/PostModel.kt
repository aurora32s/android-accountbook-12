package com.seom.accountbook.model.post

import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.model.setting.SettingModel

data class PostModel(
    // 기존 수입/지출 내역
    val account: Result<AccountEntity>?,
    // 수입/지출 내역 작성에 필요한 결제수단/입금계좌, 수입/지출 카테고리 정보
    val settingModel: SettingModel
)
