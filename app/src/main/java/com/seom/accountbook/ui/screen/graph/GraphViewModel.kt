package com.seom.accountbook.ui.screen.graph

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seom.accountbook.R
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.calendar.CalendarEntity
import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.data.repository.impl.AccountRepositoryImpl
import com.seom.accountbook.model.graph.OutComeByCategory
import com.seom.accountbook.ui.screen.calendar.CalendarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GraphViewModel(
    private val accountRepository: AccountRepository = AccountRepositoryImpl()
) : ViewModel() {
    private val _graphUiState = MutableStateFlow<GraphUiState>(GraphUiState.UnInitialized)
    val graphUiState: StateFlow<GraphUiState>
        get() = _graphUiState

    var currentYear = 0
    var currentMonth = 0

    fun fetchData(year: Int, month: Int) = viewModelScope.launch {
        _graphUiState.value = GraphUiState.Loading
        when (val result = accountRepository.getOutComeOnCategory(year, month)) {
            is Result.Error -> _graphUiState.value =
                GraphUiState.Error(R.string.error_history_get)
            is Result.Success -> {
                currentYear = year
                currentMonth = month
                _graphUiState.value =
                    GraphUiState.SuccessFetch(result.data)
            }
        }
    }
}

sealed interface GraphUiState {
    object UnInitialized : GraphUiState
    object Loading : GraphUiState

    data class SuccessFetch(
        val accounts: List<OutComeByCategory>
    ) : GraphUiState

    data class Error(
        @StringRes
        val errorMsg: Int
    ) : GraphUiState
}