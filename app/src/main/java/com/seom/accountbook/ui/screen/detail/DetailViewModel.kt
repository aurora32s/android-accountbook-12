package com.seom.accountbook.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.model.graph.OutComeByMonth
import com.seom.accountbook.model.history.HistoryModel
import com.seom.accountbook.usecase.GetDetailOutComeOnCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailOutComeOnCategory: GetDetailOutComeOnCategoryUseCase
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