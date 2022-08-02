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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GraphViewModel(
    private val accountRepository: AccountRepository = AccountRepositoryImpl()
) : ViewModel() {
    private val _outcome = MutableStateFlow<List<OutComeByCategory>>(emptyList())
    val outcome = _outcome.asStateFlow()

    fun fetchData(year: Int, month: Int) = viewModelScope.launch {
        when (val result = accountRepository.getOutComeOnCategory(year, month)) {
            is Result.Error -> {}
            is Result.Success.Finish -> {
                _outcome.value = result.data
            }
        }
    }
}