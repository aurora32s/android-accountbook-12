package com.seom.accountbook.data.entity

sealed interface Result<out T : Any> {
    sealed interface Success<out T : Any> : Result<T> {
        data class Finish<out T : Any>(val data: T) : Success<T>
        object Pause : Success<Nothing>
    }

    data class Error(val exception: String) : Result<Nothing>
}