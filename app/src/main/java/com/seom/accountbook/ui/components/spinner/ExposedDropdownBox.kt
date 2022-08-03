package com.seom.accountbook.ui.components.spinner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.seom.accountbook.R
import com.seom.accountbook.model.base.BaseModel
import com.seom.accountbook.ui.components.text.CustomText
import com.seom.accountbook.ui.theme.ColorPalette

@Composable
fun ExposedDropdownBox(
    selectedOptionId: Long,
    onOptionSelected: (Long?) -> Unit,
    onPushAddButton: () -> Unit,
    options: List<BaseModel>
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedOption = options.find { it.id == selectedOptionId }
    var dropDownWidth by remember { mutableStateOf(Size.Zero) }
    var rotateDegree by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    rotateDegree = 180f
                    expanded = true
                }
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    dropDownWidth = coordinates.size.toSize()
                },
        ) {
            CustomText(
                text = selectedOption?.name ?: "선택하세요.",
                style = MaterialTheme.typography.subtitle1,
                color = if (selectedOption == null) ColorPalette.LightPurple
                else ColorPalette.Purple,
                bold = true
            )
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = ColorPalette.Purple),
                modifier = Modifier.rotate(rotateDegree)
            )
        }

        MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(14.dp))) {

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    rotateDegree = 0f
                    expanded = false
                },
                offset = DpOffset(x = 0.dp, y = 8.dp),
                modifier = Modifier
                    .width(with(LocalDensity.current) { dropDownWidth.width.toDp() })
                    .border(
                        width = 1.dp,
                        color = ColorPalette.Purple,
                        shape = RoundedCornerShape(14.dp)
                    )
            ) {
                options.forEach {
                    DropdownMenuItem(
                        onClick = {
                            onOptionSelected(it.id)
                            expanded = false
                        },
                        modifier = Modifier
                            .height(36.dp)
                            .background(Color.Transparent),
                    ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.subtitle1.copy(color = ColorPalette.Purple)
                        )
                    }
                }
                DropdownMenuItem(
                    onClick = { onPushAddButton() },
                    modifier = Modifier
                        .height(36.dp)
                        .background(Color.Transparent),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "추가하기",
                            style = MaterialTheme.typography.subtitle1.copy(color = ColorPalette.Purple)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_plus),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = ColorPalette.Purple),
                        )
                    }
                }
            }
        }
    }
}