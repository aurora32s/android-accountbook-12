package com.seom.accountbook.model.detail

import com.seom.accountbook.model.graph.OutComeByMonth
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.history.HistoryEntity
import com.seom.accountbook.model.history.HistoryModel

data class DetailOutComeOnCategory(
    val outComeOnMonth: Result<List<OutComeByMonth>>,
    val accounts: Result<List<HistoryEntity>>
)
