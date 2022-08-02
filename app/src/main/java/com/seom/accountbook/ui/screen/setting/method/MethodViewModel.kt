package com.seom.accountbook.ui.screen.setting.method

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seom.accountbook.R
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.data.repository.MethodRepository
import com.seom.accountbook.data.repository.impl.MethodRepositoryImpl
import com.seom.accountbook.ui.screen.setting.category.CategoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MethodViewModel(
    private val methodRepository: MethodRepository = MethodRepositoryImpl()
) : ViewModel() {
    private val _methodUiState = MutableStateFlow<MethodUiState>(MethodUiState.UnInitialized)
    val methodUiState: StateFlow<MethodUiState>
        get() = _methodUiState

    private var currentMethodId: Long? = null

    private val _name = MutableStateFlow("")
    var name = _name.asStateFlow()
    fun setName(newName: String) {
        _name.value = newName
    }

    fun fetchCategory(methodId: Long?) = viewModelScope.launch {
        if (methodId == null) {
            _methodUiState.value = MethodUiState.Success.FetchMethod
        } else {
            _methodUiState.value = MethodUiState.Loading
            when (val result = methodRepository.getMethod(methodId)) {
                is Result.Error -> _methodUiState.value =
                    MethodUiState.Error(R.string.error_method_get)
                is Result.Success -> {
                    val method = result.data
                    currentMethodId = method.id
                    _name.value = method.name

                    _methodUiState.value = MethodUiState.Success.FetchMethod
                }
            }
        }
    }

    fun addCategory() = viewModelScope.launch {
        val method = MethodEntity(
            id = currentMethodId,
            name = name.value
        )

        val result = currentMethodId?.let {
            methodRepository.updateMethod(method)
        } ?: kotlin.run {
            methodRepository.addMethod(method)
        }
        when (result) {
            is Result.Error -> _methodUiState.value =
                MethodUiState.Error(R.string.error_method_add)
            is Result.Success -> _methodUiState.value = MethodUiState.Success.AddMethod
        }
    }
}

sealed interface MethodUiState {
    object UnInitialized : MethodUiState
    object Loading : MethodUiState
    sealed interface Success: MethodUiState {
        object AddMethod : Success
        object FetchMethod : Success
    }

    data class Error(
        @StringRes
        val errorMsg: Int
    ) : MethodUiState
}