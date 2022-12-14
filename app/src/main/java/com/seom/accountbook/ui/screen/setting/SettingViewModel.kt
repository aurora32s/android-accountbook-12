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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getAllSettingDataUseCase: GetAllSettingDataUseCase
) : ViewModel() {
    private val _methods = MutableStateFlow<List<MethodModel>>(emptyList())
    var methods = _methods.asStateFlow()

    private val _category = MutableStateFlow<List<CategoryModel>>(emptyList())
    var category = _category.asStateFlow()

    fun fetchData() = viewModelScope.launch {
        val result = getAllSettingDataUseCase()

        when (val methodResult = result.methods) {
            is Result.Success.Finish -> _methods.value = methodResult.data.map { it.toModel() }
            else -> {}
        }
        when (val categoryResult = result.categories) {
            is Result.Success.Finish -> _category.value = categoryResult.data.map { it.toModel() }
            else -> {}
        }
    }
}