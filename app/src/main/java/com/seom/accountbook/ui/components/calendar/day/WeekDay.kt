package com.seom.accountbook.ui.components.calendar.day

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
internal class WeekDay(
    override val date: LocalDate,
    override val isCurrentDay: Boolean,
    override val isFromCurrentMonth: Boolean
): Day