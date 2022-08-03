package com.seom.accountbook.ui.screen.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.seom.accountbook.model.graph.OutComeByMonth
import com.seom.accountbook.model.history.HistoryModel
import com.seom.accountbook.ui.components.BackButtonOneAppBar
import com.seom.accountbook.ui.components.common.BaseDivider
import com.seom.accountbook.ui.components.graph.LinearGraph
import com.seom.accountbook.ui.components.history.HistoryList
import com.seom.accountbook.ui.theme.ColorPalette
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(
    year: Int,
    month: Int,
    categoryId: String,
    viewModel: DetailViewModel = hiltViewModel(),
    onBackButtonPressed: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchData(categoryId.toLong(), year, month)
    }

    val accounts = viewModel.history.collectAsState()
    val outcome = viewModel.outComeOnMonth.collectAsState()

    if (accounts.value.isNotEmpty()) {
        DetailBody(
            accounts = accounts.value,
            month = month,
            outComeByMonth = outcome.value,
            onBackButtonPressed = onBackButtonPressed
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailBody(
    month: Int,
    accounts: List<HistoryModel>,
    outComeByMonth: List<OutComeByMonth>,
    onBackButtonPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            BackButtonOneAppBar(
                title = accounts.first().categoryName ?: "UnKnow",
                onClickBackBtn = onBackButtonPressed
            )
        }
    ) {
        Column {
            OutComeGraph(month = month, outComeByMonth = outComeByMonth)
            BaseDivider(color = ColorPalette.LightPurple)
            Spacer(modifier = Modifier.height(16.dp))
            HistoryList(
                historyGroupedByDate = accounts.groupBy { LocalDate.of(it.year, it.month, it.date) },
                bottomSpacer = 20
            )
        }
    }
}

@Composable
fun OutComeGraph(
    month: Int,
    outComeByMonth: List<OutComeByMonth>
) {
    LinearGraph(
        data = outComeByMonth,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = ColorPalette.White,
        lineColor = Brush.verticalGradient(
            colors = listOf(
                ColorPalette.LightPurple,
                ColorPalette.Purple
            )
        ),
        primaryTextColor = ColorPalette.Purple,
        secondaryTextColor = ColorPalette.LightPurple,
        keys = (5 downTo 0).map { index ->
            (if (month - index > 0) month - index else 12 + month - index
                    ).toString()
        }
    )
}
