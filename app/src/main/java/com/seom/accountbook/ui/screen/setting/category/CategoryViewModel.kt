package com.seom.accountbook.ui.screen.setting.category

import androidx.annotation.StringRes
import androidx.compose.material.rememberScaffoldState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.seom.accountbook.R
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.repository.CategoryRepository
import com.seom.accountbook.data.repository.impl.CategoryRepositoryImpl
import com.seom.accountbook.model.category.incomeColor
import com.seom.accountbook.model.category.outcomeColor
import com.seom.accountbook.model.history.HistoryType
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository = CategoryRepositoryImpl()
) : ViewModel() {
    private val _categoryUiState = MutableStateFlow<CategoryUiState>(CategoryUiState.UnInitialized)
    val categoryUiState: StateFlow<CategoryUiState>
        get() = _categoryUiState

    private var currentCategoryId: Long? = null
    private var currentCategoryType = HistoryType.INCOME

    val colorList
        get() = currentCategoryType.colorList

    // 카테고리 페이지에 필요한 데이터
    private val _name = MutableStateFlow("")
    var name = _name.asStateFlow()
    fun setName(newName: String) {
        _name.value = newName
    }

    private val _color = MutableStateFlow(0L)
    var color = _color.asStateFlow()
    fun setColor(newColor: Long) {
        _color.value = newColor
    }

    fun fetchCategory(categoryId: Long?, categoryType: HistoryType) = viewModelScope.launch {
        currentCategoryType = categoryType
        if (categoryId == null) {
            _color.value = colorList[0]
            _categoryUiState.value = CategoryUiState.Success.FetchCategory
        } else {
            _categoryUiState.value = CategoryUiState.Loading
            when (val result = categoryRepository.getCategory(categoryId)) {
                is Result.Error -> _categoryUiState.value =
                    CategoryUiState.Error(R.string.error_category_get)
                is Result.Success.Finish -> {
                    val category = result.data
                    currentCategoryId = category.id
                    _name.value = category.name
                    _color.value = category.color

                    _categoryUiState.value = CategoryUiState.Success.FetchCategory
                }
            }
        }
    }

    fun addCategory() = viewModelScope.launch {
        _categoryUiState.value = CategoryUiState.Loading
        val category = CategoryEntity(
            id = currentCategoryId,
            name = name.value,
            color = color.value,
            type = currentCategoryType.type ?: HistoryType.INCOME.type
        )

        val result = currentCategoryId?.let {
            categoryRepository.updateCategory(category)
        } ?: kotlin.run {
            categoryRepository.addCategory(category)
        }

        when (result) {
            is Result.Error -> _categoryUiState.value =
                CategoryUiState.Error(R.string.error_account_add)
            is Result.Success.Finish -> _categoryUiState.value = CategoryUiState.Success.AddCategory
            Result.Success.Pause -> _categoryUiState.value =
                CategoryUiState.Duplicate("동일한 이름의 카테고리가 있어요. ð")
        }
    }
}

sealed interface CategoryUiState {
    object UnInitialized : CategoryUiState
    object Loading : CategoryUiState
    object Success {
        object AddCategory : CategoryUiState
        object FetchCategory : CategoryUiState
    }

    data class Duplicate(
        val duplicateMsg: String
    ) : CategoryUiState

    data class Error(
        @StringRes
        val errorMsg: Int
    ) : CategoryUiState
}