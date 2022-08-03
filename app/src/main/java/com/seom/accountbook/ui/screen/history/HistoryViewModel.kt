package com.seom.accountbook.ui.screen.history

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.data.repository.impl.AccountRepositoryImpl
import com.seom.accountbook.model.history.HistoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _histories = MutableStateFlow<List<HistoryModel>>(emptyList())
    val histories = _histories.asStateFlow()

    private val _selectedItem = mutableStateListOf<Long>()
    val selectedItem: MutableList<Long>
        get() = _selectedItem

    fun setSelectedItem(id: Long) {
        if (id in selectedItem) _selectedItem.remove(id)
        else _selectedItem.add(id)
    }

    fun fetchData(year: Int, month: Int) = viewModelScope.launch {
        when (val result = accountRepository.getAllAccountByDate(year, month)) {
            is Result.Success.Finish -> _histories.value = result.data.map { it.toModel() }
            else -> {}
        }
    }

    fun removeItems() = viewModelScope.launch {
        when (accountRepository.removeAccounts(selectedItem)) {
            is Result.Success.Finish -> {
                _histories.value = _histories.value.filter { (it.id in selectedItem).not() }
                selectedItem.clear()
            }
            else -> {}
        }
    }
}