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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingViewModel(
    private val getAllSettingDataUseCase: GetAllSettingDataUseCase = GetAllSettingDataUseCase()
) : ViewModel() {
    private val _settingUiState = MutableStateFlow<SettingUiState>(SettingUiState.UnInitialized)
    val settingUiState: StateFlow<SettingUiState>
        get() = _settingUiState

    fun fetchData() = viewModelScope.launch {
        val result = getAllSettingDataUseCase()

        val methods = when (val methodResult = result.methods) {
            is Result.Error -> {
                _settingUiState.value = SettingUiState.Error(R.string.error_setting_method_get)
                emptyList()
            }
            is Result.Success -> methodResult.data
        }
        val categories = when (val categoryResult = result.categories) {
            is Result.Error -> {
                _settingUiState.value = SettingUiState.Error(R.string.error_setting_category_get)
                emptyList()
            }
            is Result.Success -> categoryResult.data
        }

        _settingUiState.value = SettingUiState.Success(
            methods = methods,
            incomeCategories = categories.filter { it.type == HistoryType.INCOME.type },
            outcomeCategories = categories.filter { it.type == HistoryType.OUTCOME.type }
        )
    }
}

sealed interface SettingUiState {
    object UnInitialized : SettingUiState
    object Loading : SettingUiState
    data class Success(
        val methods: List<MethodEntity>,
        val incomeCategories: List<CategoryEntity>,
        val outcomeCategories: List<CategoryEntity>
    ) : SettingUiState

    data class Error(
        @StringRes
        val errorMsg: Int
    ) : SettingUiState
}