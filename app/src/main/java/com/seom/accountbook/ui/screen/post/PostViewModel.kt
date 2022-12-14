package com.seom.accountbook.ui.screen.post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seom.accountbook.R
import com.seom.accountbook.data.entity.Result
import com.seom.accountbook.data.entity.account.AccountEntity
import com.seom.accountbook.data.entity.category.CategoryEntity
import com.seom.accountbook.data.entity.method.MethodEntity
import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.data.repository.impl.AccountRepositoryImpl
import com.seom.accountbook.model.category.CategoryModel
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.model.method.MethodModel
import com.seom.accountbook.usecase.GetPostDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class PostViewModel @Inject constructor(
    private val getPostDataUseCase: GetPostDataUseCase,
    private val accountRepository: AccountRepository,
) : ViewModel() {
    private val _postUIState = MutableStateFlow<PostUiState>(PostUiState.UnInitialized)
    val postUiState: StateFlow<PostUiState>
        get() = _postUIState

    private var accountId: Long? = null

    // 작성 페이지에 필요한 데이터
    private val _type = MutableStateFlow(HistoryType.INCOME)
    var type = _type.asStateFlow()
    fun setType(newType: HistoryType) {
        _type.value = newType
    }

    private val _date = MutableStateFlow(LocalDate.now())
    var date = _date.asStateFlow()
    fun setDate(newDate: LocalDate) {
        _date.value = newDate
    }

    private val _count = MutableStateFlow(0)
    var count = _count.asStateFlow()
    fun setCount(newCount: Int) {
        _count.value = newCount
    }

    private val _methodId = MutableStateFlow<Long>(-1)
    var methodId = _methodId.asStateFlow()
    fun setMethodID(newMethodId: Long) {
        _methodId.value = newMethodId
    }

    private val _categoryId = MutableStateFlow<Long>(-1)
    var categoryId = _categoryId.asStateFlow()
    fun resetCategoryId() {
        _categoryId.value = -1
    }

    fun setCategoryId(newCategoryId: Long) {
        _categoryId.value = newCategoryId
    }

    private val _content = MutableStateFlow("")
    var content = _content.asStateFlow()
    fun setContent(newContent: String) {
        _content.value = newContent
    }

    private val _methods = MutableStateFlow<List<MethodModel>>(emptyList())
    var methods = _methods.combine(_type) { method, type ->
        method.filter { it.type == type }
    }

    private val _category = MutableStateFlow<List<CategoryModel>>(emptyList())
    var category = _category.combine(_type) { category, type ->
        category.filter { it.type == type }
    }

    // 수입/지출 내역 작성 시 필요한 데이터
    fun fetchAccount(postId: Long?) = viewModelScope.launch {
        _postUIState.value = PostUiState.Loading

        val result = getPostDataUseCase(postId)

        when (val accountResult = result.account) {
            is Result.Error -> _postUIState.value =
                PostUiState.Error("정보를 가져오는 도중 문제가 발생했어요.")
            is Result.Success.Finish -> {
                val account = accountResult.data
                accountId = account.id
                _type.value = HistoryType.getHistoryType(account.type)
                _date.value = LocalDate.of(account.year, account.month, account.date)
                _count.value = account.count
                _methodId.value = account.methodId
                _categoryId.value = account.categoryId
                _content.value = account.content ?: ""
            }
            else -> {}
        }
        when (val methodResult = result.settingModel.methods) {
            is Result.Success.Finish -> _methods.value = methodResult.data.map { it.toModel() }
            else -> {}
        }
        when (val categoryResult = result.settingModel.categories) {
            is Result.Success.Finish -> _category.value = categoryResult.data.map { it.toModel() }
            else -> {}
        }
    }

    // 수입/지출 내역 등록/수정
    fun addAccount() = viewModelScope.launch {
        val account = AccountEntity(
            id = accountId,
            year = date.value.year,
            month = date.value.month.value,
            date = date.value.dayOfMonth,
            count = count.value,
            methodId = methodId.value,
            categoryId = categoryId.value,
            content = content.value,
            type = type.value.type
        )
        val result = accountId?.let {
            accountRepository.updateAccount(account)
        } ?: kotlin.run {
            accountRepository.addAccount(account)
        }

        when (result) {
            is Result.Error -> _postUIState.value = PostUiState.Error("정보를 저장하는 도중 문제가 발생했어요.")
            is Result.Success -> _postUIState.value = PostUiState.Success.AddAccount
        }
    }
}

sealed interface PostUiState {
    object UnInitialized : PostUiState
    object Loading : PostUiState

    object Success {
        object AddAccount : PostUiState
        data class FetchAccount(
            val methods: List<MethodEntity>,
            val incomeCategories: List<CategoryEntity>,
            val outcomeCategories: List<CategoryEntity>
        ) : PostUiState
    }

    data class Error(
        val errorMsg: String
    ) : PostUiState
}