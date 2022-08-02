package com.seom.accountbook.ui.screen.setting

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seom.accountbook.R
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.model.category.CategoryModel
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.model.method.MethodModel
import com.seom.accountbook.model.setting.SettingModel
import com.seom.accountbook.usecase.GetAllSettingDataUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingViewModel(
    private val getAllSettingDataUseCase: GetAllSettingDataUseCase = GetAllSettingDataUseCase()
) : ViewModel() {
    private val _methods = MutableStateFlow<List<MethodEntity>>(emptyList())
    var methods = _methods.asStateFlow()

    private val _category = MutableStateFlow<List<CategoryEntity>>(emptyList())
    var category = _category.asStateFlow()

    fun fetchData() = viewModelScope.launch {
        val result = getAllSettingDataUseCase()

        when (val methodResult = result.methods) {
            is Result.Error -> {}
            is Result.Success -> _methods.value = methodResult.data
        }
        when (val categoryResult = result.categories) {
            is Result.Error -> {}
            is Result.Success -> _category.value = categoryResult.data
        }
    }
}