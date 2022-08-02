package com.seom.accountbook.ui.screen.calendar

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seom.accountbook.R
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.calendar.CalendarEntity
import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.data.repository.impl.AccountRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val accountRepository: AccountRepository = AccountRepositoryImpl()
) : ViewModel() {
    private val _categoryUiState = MutableStateFlow<CalendarUiState>(CalendarUiState.UnInitialized)
    val categoryUiState: StateFlow<CalendarUiState>
        get() = _categoryUiState

    fun fetchData(year: Int, month: Int) = viewModelScope.launch {
        _categoryUiState.value = CalendarUiState.Loading
        when (val result = accountRepository.getAllAccountOnDate(year, month)) {
            is Result.Error -> _categoryUiState.value =
                CalendarUiState.Error(R.string.error_history_get)
            is Result.Success.Finish -> _categoryUiState.value =
                CalendarUiState.SuccessFetch(result.data)
        }
    }
}

sealed interface CalendarUiState {
    object UnInitialized : CalendarUiState
    object Loading : CalendarUiState

    data class SuccessFetch(
        val accounts: List<CalendarEntity>
    ) : CalendarUiState

    data class Error(
        @StringRes
        val errorMsg: Int
    ) : CalendarUiState
}