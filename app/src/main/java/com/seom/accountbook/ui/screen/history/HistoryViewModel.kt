package com.seom.accountbook.ui.screen.history

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seom.accountbook.R
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.data.repository.impl.AccountRepositoryImpl
import com.seom.accountbook.model.history.HistoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val accountRepository: AccountRepository = AccountRepositoryImpl()
) : ViewModel() {

    private val _historyUiState = MutableStateFlow<HistoryUiState>(HistoryUiState.UnInitialized)
    val historyUiState: StateFlow<HistoryUiState>
        get() = _historyUiState

    private val _selectedItem = mutableStateListOf<Long>()
    val selectedItem: MutableList<Long>
        get() = _selectedItem
    fun addSelectedItem(id: Long) {
        _selectedItem.add(id)
    }
    fun removeSelectedItem(id: Long){
        _selectedItem.remove(id)
    }

    fun fetchData(year: Int, month: Int) = viewModelScope.launch {
        _historyUiState.value = HistoryUiState.Loading
        when (val result = accountRepository.getAllAccountByDate(year, month)) {
            is Result.Error -> _historyUiState.value =
                HistoryUiState.Error(R.string.error_history_get)
            is Result.Success -> _historyUiState.value = HistoryUiState.Success(result.data)
        }
    }
}

sealed interface HistoryUiState {
    object UnInitialized : HistoryUiState
    object Loading : HistoryUiState

    data class Success(
        val histories: List<HistoryModel>
    ) : HistoryUiState

    data class Error(
        @StringRes
        val errorMsg: Int
    ) : HistoryUiState
}