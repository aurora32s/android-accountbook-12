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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val accountRepository: AccountRepository = AccountRepositoryImpl()
) : ViewModel() {
    private val _histories = MutableStateFlow<List<HistoryModel>>(emptyList())
    val histories = _histories.asStateFlow()

    private val _selectedItem = mutableStateListOf<Long>()
    val selectedItem: MutableList<Long>
        get() = _selectedItem

    fun addSelectedItem(id: Long) {
        _selectedItem.add(id)
    }

    fun removeSelectedItem(id: Long) {
        _selectedItem.remove(id)
    }

    fun fetchData(year: Int, month: Int) = viewModelScope.launch {
        when (val result = accountRepository.getAllAccountByDate(year, month)) {
            is Result.Error -> {}
            is Result.Success -> _histories.value = result.data
        }
    }

    fun removeItems() = viewModelScope.launch {
        when (accountRepository.removeAccounts(selectedItem)) {
            is Result.Error -> {}
            is Result.Success -> {
                _histories.value = _histories.value.filter { (it.id in selectedItem).not() }
                selectedItem.clear()
            }
        }
    }
}