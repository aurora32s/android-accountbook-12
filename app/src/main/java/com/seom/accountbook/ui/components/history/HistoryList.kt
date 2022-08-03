package com.seom.accountbook.ui.components.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.seom.accountbook.model.history.HistoryModel
import com.seom.accountbook.model.history.HistoryType
import com.seom.accountbook.ui.components.common.BottomSpacer
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette
import com.seom.accountbook.util.ext.fullFormat
import java.time.LocalDate

/**
 * 수입/지출 일별 내역을 보여주는 리스트 component
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryList(
    modifier: Modifier = Modifier,
    bottomSpacer: Int = 0,
    historyGroupedByDate: Map<LocalDate, List<HistoryModel>>,
    selectedItem: MutableList<Long> = mutableListOf(),
    onClickItem: (Long) -> Unit = {},
    onLongClickItem: (Long) -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (historyGroupedByDate.isEmpty()) {
            CustomText(
                text = "내역이 없습니다.",
                style = MaterialTheme.typography.subtitle2,
                color = ColorPalette.Purple,
                bold = true,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn {
                historyGroupedByDate.forEach { (date, histories) ->
                    stickyHeader {
                        HistoryListHeader(
                            date = date.fullFormat(),
                            income = histories.filter { it.type == HistoryType.INCOME }
                                .sumOf { it.money },
                            outCome = histories.filter { it.type == HistoryType.OUTCOME }
                                .sumOf { it.money }
                        )
                    }
                    items(items = histories) { history ->
                        HistoryListItem(
                            history = history,
                            selected = history.id in selectedItem,
                            modifier = Modifier
                                .combinedClickable(
                                    onClick = { onClickItem(history.id) },
                                    onLongClick = { onLongClickItem(history.id) }
                                )
                        )
                    }
                    BottomSpacer(16)
                }
                item { Spacer(modifier = Modifier.height(bottomSpacer.dp)) }
            }
        }
    }
}