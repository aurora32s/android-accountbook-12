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
import com.seom.accountbook.data.repository.AccountRepository
import com.seom.accountbook.data.repository.impl.AccountRepositoryImpl
import com.seom.accountbook.model.history.HistoryType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.concurrent.Flow

@RequiresApi(Build.VERSION_CODES.O)
class PostViewModel(
    private val accountRepository: AccountRepository = AccountRepositoryImpl()
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

    private val _methodId = MutableStateFlow<Long?>(null)
    var methodId = _methodId.asStateFlow()
    fun setMethodID(newMethodId: Long) {
        _methodId.value = newMethodId
    }

    private val _categoryId = MutableStateFlow<Long?>(null)
    var categoryId = _categoryId.asStateFlow()
    fun setCategoryId(newCategoryId: Long?) {
        _categoryId.value = newCategoryId
    }

    private val _content = MutableStateFlow("")
    var content = _content.asStateFlow()
    fun setContent(newContent: String) {
        _content.value = newContent
    }


    // 수입/지출 내역 작성 시 필요한 데이터
    fun fetchAccount(postId: Long?) = viewModelScope.launch {
        if (postId == null) {
            _postUIState.value = PostUiState.Success.FetchAccount
        } else {

            _postUIState.value = PostUiState.Loading
            when (val result = accountRepository.getAccount(postId)) {
                is Result.Error -> _postUIState.value =
                    PostUiState.Error(R.string.error_account_get)
                is Result.Success -> {
                    val account = result.data
                    accountId = account.id
                    _type.value = HistoryType.getHistoryType(account.type)
                    _date.value = LocalDate.of(account.year, account.month, account.date)
                    _count.value = account.count
                    _methodId.value = account.methodId
                    _categoryId.value = account.categoryId
                    _content.value = account.content ?: ""

                    _postUIState.value = PostUiState.Success.FetchAccount
                }
            }
        }
    }

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
            is Result.Error -> _postUIState.value = PostUiState.Error(R.string.error_account_add)
            is Result.Success -> _postUIState.value = PostUiState.Success.AddAccount
        }
    }
}

sealed interface PostUiState {
    object UnInitialized : PostUiState
    object Loading : PostUiState
    object Success {
        object AddAccount : PostUiState
        object FetchAccount : PostUiState
    }

    data class Error(
        @StringRes
        val errorMsg: Int
    ) : PostUiState
}