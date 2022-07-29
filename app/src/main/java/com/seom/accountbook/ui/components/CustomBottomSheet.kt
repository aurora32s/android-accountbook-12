package com.seom.accountbook.ui.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import com.seom.accountbook.model.common.Date

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomBottomSheet(
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    body: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { sheetContent() }) {
        body()
    }
}