package com.seom.accountbook.ui.screen.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.calendar.CalendarEntity
import com.seom.accountbook.data.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _histories = MutableStateFlow<List<CalendarEntity>>(emptyList())
    val histories = _histories.asStateFlow()

    fun fetchData(year: Int, month: Int) = viewModelScope.launch {
        when (val result = accountRepository.getAllAccountOnDate(year, month)) {
            is Result.Success.Finish -> _histories.value = result.data
            else -> {}
        }
    }
}