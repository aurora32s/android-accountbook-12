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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val accountRepository: AccountRepository = AccountRepositoryImpl()
) : ViewModel() {

    private val _histories = MutableStateFlow<List<CalendarEntity>>(emptyList())
    val histories = _histories.asStateFlow()

    fun fetchData(year: Int, month: Int) = viewModelScope.launch {
        when (val result = accountRepository.getAllAccountOnDate(year, month)) {
            is Result.Error -> {}
            is Result.Success.Finish -> _histories.value = result.data
        }
    }
}