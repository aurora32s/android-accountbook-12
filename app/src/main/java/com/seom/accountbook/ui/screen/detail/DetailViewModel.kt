package com.seom.accountbook.ui.screen.detail

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seom.accountbook.R
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.model.graph.OutComeByCategory
import com.seom.accountbook.model.graph.OutComeByMonth
import com.seom.accountbook.model.history.HistoryModel
import com.seom.accountbook.ui.screen.graph.GraphUiState
import com.seom.accountbook.usecase.GetDetailOutComeOnCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getDetailOutComeOnCategory: GetDetailOutComeOnCategoryUseCase = GetDetailOutComeOnCategoryUseCase()
): ViewModel() {

    private val _history = MutableStateFlow<List<HistoryModel>>(emptyList())
    val history = _history.asStateFlow()

    private val _outcomeOnMonth = MutableStateFlow<List<OutComeByMonth>>(emptyList())
    val outComeOnMonth = _outcomeOnMonth.asStateFlow()

    fun fetchData(categoryId: Long, year: Int, month: Int) = viewModelScope.launch {
        val result = getDetailOutComeOnCategory(categoryId,year,month)

         when(val data = result.outComeOnMonth) {
            is Result.Success.Finish -> _outcomeOnMonth.value = data.data
            else -> _history.value = emptyList()
        }
        when(val data = result.accounts) {
            is Result.Success.Finish-> _history.value = data.data
            else -> _history.value = emptyList()
        }
    }
}