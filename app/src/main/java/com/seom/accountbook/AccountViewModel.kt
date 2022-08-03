package com.seom.accountbook

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class AccountViewModel : ViewModel() {

    private val _year = MutableStateFlow(0)
    val year = _year.asStateFlow()
    fun setYear(newYear: Int) {
        _year.value = newYear
    }

    private val _month = MutableStateFlow(0)
    val month = _month.asStateFlow()
    fun setMonth(newMonth: Int) {
        _month.value = newMonth
    }

    init {
        val current = LocalDate.now()

        _year.value = current.year
        _month.value = current.month.value
    }

    fun setDate(newYear: Int, newMonth: Int) {
        _year.value = newYear
        _month.value = newMonth
    }
}