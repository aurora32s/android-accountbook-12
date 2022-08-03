package com.seom.accountbook.ui.screen.graph

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.data.repository.impl.AccountRepositoryImpl
import com.seom.accountbook.model.graph.OutComeByCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    private val accountRepository: AccountRepository
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