package com.seom.accountbook.ui.screen.post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
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
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.concurrent.Flow

@RequiresApi(Build.VERSION_CODES.O)
class PostViewModel(
    val accountRepository: AccountRepository = AccountRepositoryImpl()
) : ViewModel() {
    private val _postUIState = MutableStateFlow<PostUiState>(PostUiState.UnInitialized)
    val postUiState: StateFlow<PostUiState>
        get() = _postUIState

    fun addAccount() = viewModelScope.launch {
        val result = accountRepository.addAccount(
            AccountEntity(
                content = "test",
                year = 2022,
                month = 7,
                date = 31,
                count = 11000,
                type = HistoryType.INCOME.type
            )
        )

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