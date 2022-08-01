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
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getDetailOutComeOnCategory: GetDetailOutComeOnCategoryUseCase = GetDetailOutComeOnCategoryUseCase()
): ViewModel() {
    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.UnInitialized)
    val detailUiState: StateFlow<DetailUiState>
        get() = _detailUiState

    fun fetchData(categoryId: Long, year: Int, month: Int) = viewModelScope.launch {
        _detailUiState.value = DetailUiState.Loading
        println("************")
        val result = getDetailOutComeOnCategory(categoryId,year,month)

        val outComeByMonth = when(val data = result.outComeOnMonth) {
            is Result.Error -> emptyList()
            is Result.Success -> data.data
        }
        val accounts = when(val data = result.accounts) {
            is Result.Error -> emptyList()
            is Result.Success -> data.data
        }
        _detailUiState.value = DetailUiState.SuccessFetch(
            outComeByMonth = outComeByMonth,
            accounts = accounts
        )
    }
}

sealed interface DetailUiState {
    object UnInitialized : DetailUiState
    object Loading : DetailUiState

    data class SuccessFetch(
        val outComeByMonth: List<OutComeByMonth>,
        val accounts: List<HistoryModel>
    ) : DetailUiState

    data class Error(
        @StringRes
        val errorMsg: Int
    ) : DetailUiState
}