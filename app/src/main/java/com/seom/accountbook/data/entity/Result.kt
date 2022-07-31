package com.seom.accountbook.data.entity

sealed interface Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>
    data class Error(val exception: String) : Result<Nothing>
}